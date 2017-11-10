package me.command.liblowlevel.pointer;

public class IntPointer extends Pointer<Integer> {

    public IntPointer(long pointee){
        super(pointee);
        size = 4;
    }

    @Override
    public Integer getPointee() {
        return theUnsafe.getInt(pointee);
    }

    @Override
    public void setPointee(Integer to) {
        theUnsafe.putInt(pointee, to);
    }

}
