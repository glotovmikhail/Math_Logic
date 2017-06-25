/**
 * Created by Михаил on 23.10.2016.
 */
public enum Operations {
    AND, OR, NEG, IMPL;

    public static int getPriority(Operations oper) {
        switch (oper) {
            case IMPL: {
                return 3;
            }
            case OR: {
                return 2;
            }
            case AND: {
                return 1;
            }
            case NEG: {
                return 0;
            }
            default: {
                return -1;
            }
        }
    }


    public enum Parts {
        LEFT, RIGHT;
    }

    public static Parts getPart(Operations oper) {
        switch (oper) {
            case IMPL: {
                return Parts.RIGHT;
            }
            default: {
                return Parts.LEFT;
            }
        }
    }

}
