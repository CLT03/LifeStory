package com.vivwe.faceunity.base;

import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.ScaleGestureDetector;
import com.vivwe.faceunity.core.AvatarHandle;
import com.vivwe.faceunity.core.FUP2ARenderer;
import com.vivwe.faceunity.core.P2ACore;
import com.vivwe.faceunity.entity.AvatarP2A;
import com.vivwe.faceunity.renderer.CameraRenderer;

/**
 * ahtor: super_link
 * date: 2019/4/25 10:46
 * remark:
 */
public class BaseFragment extends Fragment {

    private CameraRenderer mCameraRenderer;
    private FUP2ARenderer mFUP2ARenderer;
    private P2ACore mP2ACore;
    private AvatarHandle mAvatarHandle;
    private AvatarP2A mShowAvatarP2A;
    private GestureDetectorCompat mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;


}
