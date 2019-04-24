package com.wd.airdemo.module;

import com.wd.ms.ITaskBinder;
import com.wd.ms.tools.MSTools;

public class AirModule {
	private static CarBusCallBack callBack;

	public static void Init() {
		System.out.println("airserver create");
		ITaskBinder module = MSTools.getInstance().getModule(FinalRemoteModule.MODULE_CARBUS);
		RemoteTools.setTaskBinder(module);
		callBack = new CarBusCallBack();
		regFlags();
	}

	// unused
/*	public static void Close() {
		MyApp.getOBJ().requestNullSource();
		System.out.println("airserver destroy");
		unregFlags();
		RemoteTools.setTaskBinder(null);
	}*/

	private static void regFlags() {
		for (int i = 0; i < FinalCanbus.U_MAX; i++)
			RemoteTools.register(callBack, i, 1);
	}

	// unused
/*	private static void unregFlags() {
		for (int i = 0; i < FinalRadio.U_CNT_MAX; i++)
			RemoteTools.unregister(callBack, i);
	}*/
}
