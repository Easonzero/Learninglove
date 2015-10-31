package com.wangyi.learninglove;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.os.Bundle;


public class OnLock extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        if (devicePolicyManager.isAdminActive(DAR.getCn(this))) {
            devicePolicyManager.lockNow();
            finish();
        }
        else{
        	startAddDeviceAdminAty();
        }
	}
	private void startAddDeviceAdminAty(){
		Intent i = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        	i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
        			DAR.getCn(this));
        	i.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
        			"注册此组件后才能拥有锁屏功能");

        	startActivityForResult(i, REQUEST_CODE_ADD_DEVICE_ADMIN);
	}

	@Override

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode==Activity.RESULT_OK) {
            devicePolicyManager.lockNow();
            finish();
        }else{
                startAddDeviceAdminAty();
        }

		super.onActivityResult(requestCode, resultCode, data);
	}

	private DevicePolicyManager devicePolicyManager=null;
	private static final int REQUEST_CODE_ADD_DEVICE_ADMIN=10001;
}
