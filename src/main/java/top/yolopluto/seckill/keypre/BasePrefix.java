package top.yolopluto.seckill.keypre;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/10 20:11
 * @description: 抽象类
 * @Modified By:
 */
public abstract class BasePrefix implements KeyPrefix{
    private int expireSeconds;

    private String prefix;

    //默认0代表永不过期
    public BasePrefix(String prefix){
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();//拿到参数类类名
        return className + ":" + prefix;
    }
}
