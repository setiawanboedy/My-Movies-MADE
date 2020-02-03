package com.attafakkur.myfilmfinal.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * @author geek-dev
 */
public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());

    }

}
