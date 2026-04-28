package constants;

public final class Endpoints {
    public static final String HEALTH = "/health";
    public static final String LOGIN = "/auth/login";
    public static final String PRODUCTS = "/products";
    public static final String PRODUCTS_BY_ID = "/products/{id}";
    public static final String CART = "/cart";
    public static final String CART_ITEMS = "/cart/items";
    public static final String CART_ITEM_BY_ID = "/cart/items/{productId}";
    public static final String CHECKOUT = "/checkout";

    private Endpoints() {
        throw new UnsupportedOperationException("Endpoints is a constants class and cannot be instantiated");
    }
}