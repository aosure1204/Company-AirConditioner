/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\workspace\\norman\\AndroidStudio\\MyApp\\MainServer\\src\\main\\aidl\\com\\wd\\com.wd.libmstools.ms\\IRemoteModule.aidl
 */
package com.wd.ms;

public interface IRemoteModule extends android.os.IInterface {
    public ITaskBinder getModule(int code) throws android.os.RemoteException;
    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends android.os.Binder implements IRemoteModule {
        private static final String DESCRIPTOR = "com.wd.ms.IRemoteModule";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.wd.com.wd.libmstools.ms.IRemoteModule interface,
         * generating a proxy if needed.
         */
        public static IRemoteModule asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof IRemoteModule))) {
                return ((IRemoteModule) iin);
            }
            return new Stub.Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_getModule: {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    ITaskBinder _result = this.getModule(_arg0);
                    reply.writeNoException();
                    reply.writeStrongBinder((((_result != null)) ? (_result.asBinder()) : (null)));
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements IRemoteModule {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public ITaskBinder getModule(int code) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                ITaskBinder _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(code);
                    mRemote.transact(Stub.TRANSACTION_getModule, _data, _reply, 0);
                    _reply.readException();
                    _result = ITaskBinder.Stub.asInterface(_reply.readStrongBinder());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
        }

        static final int TRANSACTION_getModule = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    }

}
