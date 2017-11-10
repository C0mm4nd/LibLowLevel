package me.command.liblowlevel.pointer;

public class LongPointer extends Pointer<Long> {

    public LongPointer(long pointee){
        super(pointee);
        size = 8;
    }

    @Override
    public Long getPointee() {
        return theUnsafe.getLong(pointee);
    }

    @Override
    public void setPointee(Long to) {
        theUnsafe.putLong(pointee, to);
    }

}
