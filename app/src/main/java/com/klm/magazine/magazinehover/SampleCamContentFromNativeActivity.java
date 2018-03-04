package com.klm.magazine.magazinehover;

import android.os.Bundle;

public class SampleCamContentFromNativeActivity extends SampleCamActivity {
	
	@Override
	protected void onPostCreate( final Bundle savedInstanceState ) {
		super.onPostCreate( savedInstanceState );
		this.injectData();
	}
}