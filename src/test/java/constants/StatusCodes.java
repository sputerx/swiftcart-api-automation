package constants;

public final class StatusCodes {
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int INTERNAL_SERVER_ERROR = 500;

    private StatusCodes() {
        throw new UnsupportedOperationException("StatusCodes is a constants class and cannot be instantiated");
    }
}
