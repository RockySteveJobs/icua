/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\iCua\\nuworkspace\\nuiCua\\src\\iCua\\Services\\IRemoteServiceCallback.aidl
 */
package iCua.Services;
import java.lang.String;
import android.os.RemoteException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Binder;
import android.os.Parcel;
/**
 * Example of a callback interface used by IRemoteService to send
 * synchronous notifications back to its clients.  Note that this is a
 * one-way interface so the server does not block waiting for the client.
 */
public interface IRemoteServiceCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements iCua.Services.IRemoteServiceCallback
{
private static final java.lang.String DESCRIPTOR = "iCua.Services.IRemoteServiceCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an IRemoteServiceCallback interface,
 * generating a proxy if needed.
 */
public static iCua.Services.IRemoteServiceCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
iCua.Services.IRemoteServiceCallback in = (iCua.Services.IRemoteServiceCallback)obj.queryLocalInterface(DESCRIPTOR);
if ((in!=null)) {
return in;
}
return new iCua.Services.IRemoteServiceCallback.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_valueChanged:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.valueChanged(_arg0);
return true;
}
case TRANSACTION_songChanged:
{
data.enforceInterface(DESCRIPTOR);
this.songChanged();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements iCua.Services.IRemoteServiceCallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Called when the service has a new value for you.
     */
public void valueChanged(int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_valueChanged, _data, null, IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void songChanged() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_songChanged, _data, null, IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_valueChanged = (IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_songChanged = (IBinder.FIRST_CALL_TRANSACTION + 1);
}
/**
     * Called when the service has a new value for you.
     */
public void valueChanged(int value) throws android.os.RemoteException;
public void songChanged() throws android.os.RemoteException;
}
