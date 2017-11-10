package me.command.liblowlevel.pointer;

public class BytePointer extends Pointer<Byte> {

    public BytePointer(long pointee){
        super(pointee);
        size = 1;
    }

    @Override
    public Byte getPointee() {
        return theUnsafe.getByte(pointee);
    }

    @Override
    public void setPointee(Byte to) {
        theUnsafe.putByte(pointee, to);
    }

}
