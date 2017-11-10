package me.command.liblowlevel.pointer;

public class CharPointer extends Pointer<Character> {

    public CharPointer(long pointee){
        super(pointee);
        size = 2;
    }

    @Override
    public Character getPointee() {
        return theUnsafe.getChar(pointee);
    }

    @Override
    public void setPointee(Character to) {
        theUnsafe.putChar(pointee, to);
    }

}
