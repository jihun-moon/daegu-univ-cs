package com.example.mobiledoctor;

public class Medicine {

    private final String name, efficacy, usage, price;
    private final int imageResId;           // ← 추가

    public Medicine(String name, String efficacy, String usage, String price, int imageResId) {
        this.name       = name;
        this.efficacy   = efficacy;
        this.usage      = usage;
        this.price      = price;
        this.imageResId = imageResId;
    }

    /** 약 이름 반환 */
    public String getName() {
        return name;
    }

    /** 효능 반환 */
    public String getEfficacy() {
        return efficacy;
    }

    /** 복용법 반환 */
    public String getUsage() {
        return usage;
    }

    /** 가격 반환 */
    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    @Override
    public String toString() {
        return "이름: "      + name
                + "\n효능: "     + efficacy
                + "\n복용법: "   + usage
                + "\n가격: "     + price;
    }
}
