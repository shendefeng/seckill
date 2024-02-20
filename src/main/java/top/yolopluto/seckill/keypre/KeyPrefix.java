package top.yolopluto.seckill.keypre;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/10 20:10
 * @description: Redis的key的前缀的接口
 * @Modified By:
 */
public interface KeyPrefix {
    /**
     * 有效期
     * @return
     */
    public int expireSeconds();
    public String getPrefix();
}
