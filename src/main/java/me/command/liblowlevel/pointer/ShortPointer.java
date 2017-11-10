package me.command.liblowlevel.pointer;

public class ShortPointer extends Pointer<Short> {

    public ShortPointer(long pointee){
        super(pointee);
        size = 2;
    }

    @Override
    public Short getPointee() {
        return theUnsafe.getShort(pointee);
    }

    @Override
    public void setPointee(Short to) {
        theUnsafe.putShort(pointee, to);
    }

}
