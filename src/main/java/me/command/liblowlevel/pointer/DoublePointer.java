package me.command.liblowlevel.pointer;

public class DoublePointer extends Pointer<Double> {

    public DoublePointer(long pointee){
        super(pointee);
        size = 8;
    }

    @Override
    public Double getPointee() {
        return theUnsafe.getDouble(pointee);
    }

    @Override
    public void setPointee(Double to) {
        theUnsafe.putDouble(pointee, to);
    }

}
