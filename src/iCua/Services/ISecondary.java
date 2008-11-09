/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\iCua\\nuworkspace\\nuiCua\\src\\iCua\\Services\\ISecondary.aidl
 */
package iCua.Services;
import java.lang.String;
import android.os.RemoteException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Binder;
import android.os.Parcel;
/**
 * Example of a secondary interface associated with a service.  (Note that
 * the interface itself doesn't impact, it is just a matter of how you
 * retrieve it from the service.)
 */
public interface ISecondary extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements iCua.Services.ISecondary
{
private static final java.lang.String DESCRIPTOR = "iCua.Services.ISecondary";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an ISecondary interface,
 * generating a proxy if needed.
 */
public static iCua.Services.ISecondary asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
iCua.Services.ISecondary in = (iCua.Services.ISecondary)obj.queryLocalInterface(DESCRIPTOR);
if ((in!=null)) {
return in;
}
return new iCua.Services.ISecondary.Stub.Proxy(obj);
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
case TRANSACTION_getPid:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPid();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isTimeStamp:
{
data.enforceInterface(DESCRIPTOR);
long _arg0;
_arg0 = data.readLong();
boolean _result = this.isTimeStamp(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setTimeStamp:
{
data.enforceInterface(DESCRIPTOR);
long _arg0;
_arg0 = data.readLong();
this.setTimeStamp(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getArtist:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getArtist();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getDuration:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDuration();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getTitle:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getTitle();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getIdAlbum:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getIdAlbum();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getIdArtist:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getIdArtist();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_playSong:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.playSong();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_stopSong:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.stopSong();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_nextSong:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.nextSong();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_prevSong:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.prevSong();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_seekSong:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.seekSong(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_SetPlaylist:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
this.SetPlaylist(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_isPlaying:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isPlaying();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_basicTypes:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
long _arg1;
_arg1 = data.readLong();
boolean _arg2;
_arg2 = (0!=data.readInt());
float _arg3;
_arg3 = data.readFloat();
double _arg4;
_arg4 = data.readDouble();
java.lang.String _arg5;
_arg5 = data.readString();
this.basicTypes(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements iCua.Services.ISecondary
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
     * Request the PID of this service, to do evil things with it.
     */
public int getPid() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPid, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean isTimeStamp(long ts) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(ts);
mRemote.transact(Stub.TRANSACTION_isTimeStamp, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setTimeStamp(long ts) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(ts);
mRemote.transact(Stub.TRANSACTION_setTimeStamp, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public java.lang.String getArtist() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getArtist, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getDuration() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDuration, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public java.lang.String getTitle() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getTitle, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getIdAlbum() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getIdAlbum, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getIdArtist() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getIdArtist, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int playSong() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_playSong, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int stopSong() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopSong, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int nextSong() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_nextSong, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int prevSong() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_prevSong, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void seekSong(int pos) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(pos);
mRemote.transact(Stub.TRANSACTION_seekSong, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void SetPlaylist(java.lang.String song, java.lang.String artist, java.lang.String album) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(song);
_data.writeString(artist);
_data.writeString(album);
mRemote.transact(Stub.TRANSACTION_SetPlaylist, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean isPlaying() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isPlaying, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * This demonstrates the basic types that you can use as parameters
     * and return values in AIDL.
     */
public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(anInt);
_data.writeLong(aLong);
_data.writeInt(((aBoolean)?(1):(0)));
_data.writeFloat(aFloat);
_data.writeDouble(aDouble);
_data.writeString(aString);
mRemote.transact(Stub.TRANSACTION_basicTypes, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getPid = (IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_isTimeStamp = (IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setTimeStamp = (IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getArtist = (IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getDuration = (IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getTitle = (IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getIdAlbum = (IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getIdArtist = (IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_playSong = (IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_stopSong = (IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_nextSong = (IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_prevSong = (IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_seekSong = (IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_SetPlaylist = (IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_isPlaying = (IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_basicTypes = (IBinder.FIRST_CALL_TRANSACTION + 15);
}
/**
     * Request the PID of this service, to do evil things with it.
     */
public int getPid() throws android.os.RemoteException;
public boolean isTimeStamp(long ts) throws android.os.RemoteException;
public void setTimeStamp(long ts) throws android.os.RemoteException;
public java.lang.String getArtist() throws android.os.RemoteException;
public int getDuration() throws android.os.RemoteException;
public java.lang.String getTitle() throws android.os.RemoteException;
public int getIdAlbum() throws android.os.RemoteException;
public int getIdArtist() throws android.os.RemoteException;
public int playSong() throws android.os.RemoteException;
public int stopSong() throws android.os.RemoteException;
public int nextSong() throws android.os.RemoteException;
public int prevSong() throws android.os.RemoteException;
public void seekSong(int pos) throws android.os.RemoteException;
public void SetPlaylist(java.lang.String song, java.lang.String artist, java.lang.String album) throws android.os.RemoteException;
public boolean isPlaying() throws android.os.RemoteException;
/**
     * This demonstrates the basic types that you can use as parameters
     * and return values in AIDL.
     */
public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) throws android.os.RemoteException;
}
