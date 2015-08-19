/*
 * Created on 28/06/2015 by Felipe Cardozo
 */
package com.felipe.cardozo.entrerock.android;

import android.app.Application;

public class UserManagementApplication extends Application {

    private String serviceAPI;

    public String getServiceAPI() {
        return serviceAPI;
    }

    public void setServiceAPI(String serviceAPI) {
        this.serviceAPI = serviceAPI;
    }

}
