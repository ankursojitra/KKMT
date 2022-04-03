package com.rjsquare.kkmt.Activity.Review

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.os.*
import android.util.Log
import android.util.Size
import android.util.SparseIntArray
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import java.io.*
import java.util.*

class AndroidCameraApi : AppCompatActivity() {
    private var takePictureButton: Button? = null
    private var textureView: TextureView? = null

    companion object {
        private const val TAG = "AndroidCameraApi"
        private val ORIENTATIONS = SparseIntArray()
        private const val REQUEST_CAMERA_PERMISSION = 200

        init {
            ORIENTATIONS.append(Surface.ROTATION_0, 90)
            ORIENTATIONS.append(Surface.ROTATION_90, 0)
            ORIENTATIONS.append(Surface.ROTATION_180, 270)
            ORIENTATIONS.append(Surface.ROTATION_270, 180)
        }
    }

    private var cameraId: String? = null
    protected var cameraDevice: CameraDevice? = null
    protected var cameraCaptureSessions: CameraCaptureSession? = null
    protected var captureRequest: CaptureRequest? = null
    protected var captureRequestBuilder: CaptureRequest.Builder? = null
    private var imageDimension: Size? = null
    private var imageReader: ImageReader? = null
    private val file: File? = null
    private val mFlashSupported = false
    private var mBackgroundHandler: Handler? = null
    private var mBackgroundThread: HandlerThread? = null
    private var mCapturePreview: ImageView? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_image)
        textureView =
            findViewById<View>(R.id.texture) as TextureView
        assert(textureView != null)
        textureView!!.surfaceTextureListener = textureListener
        mCapturePreview = findViewById(R.id.capture_preview)
        takePictureButton =
            findViewById<View>(R.id.btn_takepicture) as Button
        assert(takePictureButton != null)
        takePictureButton!!.setOnClickListener { takePicture() }
    }

    var textureListener: TextureView.SurfaceTextureListener =
        object : TextureView.SurfaceTextureListener {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                //open your camera here
                openCamera()
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                // Transform you image captured size according to the surface width and height
            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
        }
    private val stateCallback: CameraDevice.StateCallback =
        object : CameraDevice.StateCallback() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            override fun onOpened(camera: CameraDevice) {
                //This is called when the camera is open
                Log.e(TAG, "onOpened")
                cameraDevice = camera
                createCameraPreview()
            }

            override fun onDisconnected(camera: CameraDevice) {
                cameraDevice!!.close()
            }

            override fun onError(
                camera: CameraDevice,
                error: Int
            ) {
                cameraDevice!!.close()
                cameraDevice = null
            }
        }
    val captureCallbackListener: CameraCaptureSession.CaptureCallback =
        object : CameraCaptureSession.CaptureCallback() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            override fun onCaptureCompleted(
                session: CameraCaptureSession,
                request: CaptureRequest,
                result: TotalCaptureResult
            ) {
                super.onCaptureCompleted(session, request, result)
                Toast.makeText(
                    this@AndroidCameraApi,
                    "Saved:$file",
                    Toast.LENGTH_SHORT
                ).show()
                createCameraPreview()
            }
        }

    protected fun startBackgroundThread() {
        mBackgroundThread = HandlerThread("Camera Background")
        mBackgroundThread!!.start()
        mBackgroundHandler = Handler(mBackgroundThread!!.looper)
    }

    protected fun stopBackgroundThread() {
        mBackgroundThread!!.quitSafely()
        try {
            mBackgroundThread!!.join()
            mBackgroundThread = null
            mBackgroundHandler = null
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected fun takePicture() {
        if (null == cameraDevice) {
            Log.e(TAG, "cameraDevice is null")
            return
        }
        var manager: CameraManager? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            manager =
                getSystemService(Context.CAMERA_SERVICE) as CameraManager
        }
        try {
            val characteristics =
                manager!!.getCameraCharacteristics(cameraDevice!!.id)
            var jpegSizes: Array<Size>? = null
            if (characteristics != null) {
                jpegSizes =
                    characteristics.get(
                        CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP
                    )!!.getOutputSizes(ImageFormat.JPEG)
            }
            var width = 640
            var height = 480
            if (jpegSizes != null && 0 < jpegSizes.size) {
                width = jpegSizes[0].width
                height = jpegSizes[0].height
            }
            val reader = ImageReader.newInstance(
                width,
                height,
                ImageFormat.JPEG,
                1
            )
            val outputSurfaces: MutableList<Surface> =
                ArrayList(2)
            outputSurfaces.add(reader.surface)
            outputSurfaces.add(Surface(textureView!!.surfaceTexture))
            val captureBuilder =
                cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            captureBuilder.addTarget(reader.surface)
            captureBuilder.set(
                CaptureRequest.CONTROL_MODE,
                CameraMetadata.CONTROL_MODE_AUTO
            )
            // Orientation
            val rotation = windowManager.defaultDisplay.rotation
            captureBuilder.set(
                CaptureRequest.JPEG_ORIENTATION,
                ORIENTATIONS[rotation]
            )
            val file = File(
                Environment.getExternalStorageDirectory().toString() + "/pic.jpg"
            )
            val readerListener: ImageReader.OnImageAvailableListener =
                object : ImageReader.OnImageAvailableListener {
                    override fun onImageAvailable(reader: ImageReader) {
                        var image: Image? = null
                        try {
                            image = reader.acquireLatestImage()
                            val buffer = image.planes[0].buffer
                            val bytes = ByteArray(buffer.capacity())
                            buffer[bytes]
                            save(bytes)
                            val bitmapImage =
                                BitmapFactory.decodeByteArray(
                                    bytes,
                                    0,
                                    bytes.size,
                                    null
                                )
                            ApplicationClass.mBiconReviewImageX = bitmapImage

                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        } finally {
                            image?.close()
                        }
                    }

                    @Throws(IOException::class)
                    private fun save(bytes: ByteArray) {
                        var output: OutputStream? = null
                        try {
                            output = FileOutputStream(file)
                            output.write(bytes)
                        } finally {
                            output?.close()
                        }
                    }
                }
            reader.setOnImageAvailableListener(readerListener, mBackgroundHandler)
            val captureListener: CameraCaptureSession.CaptureCallback =
                object : CameraCaptureSession.CaptureCallback() {
                    override fun onCaptureCompleted(
                        session: CameraCaptureSession,
                        request: CaptureRequest,
                        result: TotalCaptureResult
                    ) {
                        super.onCaptureCompleted(session, request, result)
                        Toast.makeText(
                            this@AndroidCameraApi,
                            "Saved:$file",
                            Toast.LENGTH_SHORT
                        ).show()
                        setResult(Activity.RESULT_OK)
                        finish()
//                        createCameraPreview()
                    }
                }
            cameraDevice!!.createCaptureSession(
                outputSurfaces,
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        try {
                            session.capture(
                                captureBuilder.build(),
                                captureListener,
                                mBackgroundHandler
                            )
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {}
                },
                mBackgroundHandler
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected fun createCameraPreview() {
        try {
            val texture = textureView!!.surfaceTexture!!
            texture.setDefaultBufferSize(imageDimension!!.width, imageDimension!!.height)
            val surface = Surface(texture)
            captureRequestBuilder =
                cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder!!.addTarget(surface)
            cameraDevice!!.createCaptureSession(
                Arrays.asList(surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                        //The camera is already closed
                        if (null == cameraDevice) {
                            return
                        }
                        // When the session is ready, we start displaying the preview.
                        cameraCaptureSessions = cameraCaptureSession
                        updatePreview()
                    }

                    override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
                        Toast.makeText(
                            this@AndroidCameraApi,
                            "Configuration change",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                null
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun openCamera() {
        val manager =
            getSystemService(Context.CAMERA_SERVICE) as CameraManager
        Log.e(TAG, "is camera open")
        try {
            cameraId = manager.cameraIdList[0]
            val characteristics =
                manager.getCameraCharacteristics(cameraId!!)
            val map =
                characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!
            imageDimension =
                map.getOutputSizes(SurfaceTexture::class.java)[0]
            // Add permission for camera and let user grant the permission
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@AndroidCameraApi,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    REQUEST_CAMERA_PERMISSION
                )
                return
            }
            manager.openCamera(cameraId!!, stateCallback, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
        Log.e(TAG, "openCamera X")
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected fun updatePreview() {
        if (null == cameraDevice) {
            Log.e(TAG, "updatePreview error, return")
        }
        captureRequestBuilder!!.set(
            CaptureRequest.CONTROL_MODE,
            CameraMetadata.CONTROL_MODE_AUTO
        )
        try {
            cameraCaptureSessions!!.setRepeatingRequest(
                captureRequestBuilder!!.build(),
                null,
                mBackgroundHandler
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun closeCamera() {
        if (null != cameraDevice) {
            cameraDevice!!.close()
            cameraDevice = null
        }
        if (null != imageReader) {
            imageReader!!.close()
            imageReader = null
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(
                    this@AndroidCameraApi,
                    "Sorry!!!, you can't use this app without granting permission",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume")
        startBackgroundThread()
        if (textureView!!.isAvailable) {
            openCamera()
        } else {
            textureView!!.surfaceTextureListener = textureListener
        }
    }

    override fun onPause() {
        Log.e(TAG, "onPause")
        closeCamera();
        stopBackgroundThread()
        super.onPause()
    }
}