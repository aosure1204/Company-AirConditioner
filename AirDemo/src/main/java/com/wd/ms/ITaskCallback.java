/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\workspace\\norman\\AndroidStudio\\MyApp\\MainServer\\src\\main\\aidl\\com\\wd\\com.wd.libmstools.ms\\ITaskCallback.aidl
 */
package com.wd.ms;
// Declare any non-default types here with import statements

public interface ITaskCallback extends android.os.IInterface {
    public void update(int updateCode, int[] ints, float[] flts, String[] strs) throws android.os.RemoteException;
    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends android.os.Binder implements ITaskCallback {
        private static final String DESCRIPTOR = "com.wd.ms.ITaskCallback";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.wd.com.wd.ms.ITaskCallback interface,
         * generating a proxy if needed.
         */
        public static ITaskCallback asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof ITaskCallback))) {
                return ((ITaskCallback) iin);
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
                case TRANSACTION_update: {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    int[] _arg1;
                    _arg1 = data.createIntArray();
                    float[] _arg2;
                    _arg2 = data.createFloatArray();
                    String[] _arg3;
                    _arg3 = data.createStringArray();
                    this.update(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements ITaskCallback {
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
            public void update(int updateCode, int[] ints, float[] flts, String[] strs) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(updateCode);
                    _data.writeIntArray(ints);
                    _data.writeFloatArray(flts);
                    _data.writeStringArray(strs);
                    mRemote.transact(Stub.TRANSACTION_update, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        static final int TRANSACTION_update = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    }

}
