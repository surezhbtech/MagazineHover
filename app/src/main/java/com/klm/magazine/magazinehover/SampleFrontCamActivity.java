package com.klm.magazine.magazinehover;

import com.wikitude.architect.StartupConfiguration.CameraPosition;

/**
 * This sample will use CameraPosition.FRONT on startup.
 */
public class SampleFrontCamActivity extends SampleCamActivity {

	@Override
	protected CameraPosition getCameraPosition() {
		return CameraPosition.FRONT;
	}
}
