/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\workspace\\norman\\AndroidStudio\\MyApp\\MainServer\\src\\main\\aidl\\com\\wd\\com.wd.libmstools.ms\\ITaskBinder.aidl
 */
package com.wd.ms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ITaskBinder extends IInterface {
    public void cmd(int cmd, int[] ints, float[] flts, String[] strs) throws RemoteException;

    public void registerCallback(ITaskCallback cb, int code, int update) throws RemoteException;

    public void unregisterCallback(ITaskCallback cb, int code) throws RemoteException;

    public ModuleObj get(int code, int[] ints, float[] flts, String[] strs) throws RemoteException;

    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends Binder implements ITaskBinder {
        private static final String DESCRIPTOR = "com.wd.ms.ITaskBinder";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.wd.com.wd.ms.ITaskBinder interface,
         * generating a proxy if needed.
         */
        public static ITaskBinder asInterface(IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof ITaskBinder))) {
                return ((ITaskBinder) iin);
            }
            return new Stub.Proxy(obj);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_cmd: {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    int[] _arg1;
                    _arg1 = data.createIntArray();
                    float[] _arg2;
                    _arg2 = data.createFloatArray();
                    String[] _arg3;
                    _arg3 = data.createStringArray();
                    this.cmd(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_registerCallback: {
                    data.enforceInterface(descriptor);
                    ITaskCallback _arg0;
                    _arg0 = ITaskCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg1;
                    _arg1 = data.readInt();
                    int _arg2;
                    _arg2 = data.readInt();
                    this.registerCallback(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_unregisterCallback: {
                    data.enforceInterface(descriptor);
                    ITaskCallback _arg0;
                    _arg0 = ITaskCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg1;
                    _arg1 = data.readInt();
                    this.unregisterCallback(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_get: {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    int[] _arg1;
                    _arg1 = data.createIntArray();
                    float[] _arg2;
                    _arg2 = data.createFloatArray();
                    String[] _arg3;
                    _arg3 = data.createStringArray();
                    ModuleObj _result = this.get(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    if ((_result != null)) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements ITaskBinder {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                mRemote = remote;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public void cmd(int cmd, int[] ints, float[] flts, String[] strs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(cmd);
                    _data.writeIntArray(ints);
                    _data.writeFloatArray(flts);
                    _data.writeStringArray(strs);
                    mRemote.transact(Stub.TRANSACTION_cmd, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }


            @Override
            public void registerCallback(ITaskCallback cb, int code, int update) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder((((cb != null)) ? (cb.asBinder()) : (null)));
                    _data.writeInt(code);
                    _data.writeInt(update);
                    mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public void unregisterCallback(ITaskCallback cb, int code) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder((((cb != null)) ? (cb.asBinder()) : (null)));
                    _data.writeInt(code);
                    mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public ModuleObj get(int code, int[] ints, float[] flts, String[] strs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                ModuleObj _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeIntArray(ints);
                    _data.writeFloatArray(flts);
                    _data.writeStringArray(strs);
                    mRemote.transact(Stub.TRANSACTION_get, _data, _reply, 0);
                    _reply.readException();
                    if ((0 != _reply.readInt())) {
                        _result = ModuleObj.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
        }

        static final int TRANSACTION_cmd = (IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_registerCallback = (IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_unregisterCallback = (IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_get = (IBinder.FIRST_CALL_TRANSACTION + 3);
    }
}
