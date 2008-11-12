/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\svn4\\src\\iCua\\Interfaces\\IPod.aidl
 */
package iCua.Interfaces;
import java.lang.String;
import android.os.RemoteException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Binder;
import android.os.Parcel;
public interface IPod extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements iCua.Interfaces.IPod
{
private static final java.lang.String DESCRIPTOR = "iCua.Interfaces.IPod";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an IPod interface,
 * generating a proxy if needed.
 */
public static iCua.Interfaces.IPod asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
iCua.Interfaces.IPod in = (iCua.Interfaces.IPod)obj.queryLocalInterface(DESCRIPTOR);
if ((in!=null)) {
return in;
}
return new iCua.Interfaces.IPod.Stub.Proxy(obj);
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
case TRANSACTION_exit:
{
data.enforceInterface(DESCRIPTOR);
this.exit();
reply.writeNoException();
return true;
}
case TRANSACTION_play:
{
data.enforceInterface(DESCRIPTOR);
this.play();
reply.writeNoException();
return true;
}
case TRANSACTION_pause:
{
data.enforceInterface(DESCRIPTOR);
this.pause();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements iCua.Interfaces.IPod
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
public void exit() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_exit, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void play() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_play, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void pause() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_pause, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_exit = (IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_play = (IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_pause = (IBinder.FIRST_CALL_TRANSACTION + 2);
}
public void exit() throws android.os.RemoteException;
public void play() throws android.os.RemoteException;
public void pause() throws android.os.RemoteException;
}
