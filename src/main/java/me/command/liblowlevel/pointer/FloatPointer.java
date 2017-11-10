package me.command.liblowlevel.pointer;

public class FloatPointer extends Pointer<Float> {

    public FloatPointer(long pointee){
        super(pointee);
        size = 4;
    }

    @Override
    public Float getPointee() {
        return theUnsafe.getFloat(pointee);
    }

    @Override
    public void setPointee(Float to) {
        theUnsafe.putFloat(pointee, to);
    }

}
