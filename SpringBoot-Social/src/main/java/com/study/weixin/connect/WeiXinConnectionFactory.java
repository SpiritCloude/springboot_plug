package com.study.weixin.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import com.study.weixin.api.WeiXin;

/**
 * 	微信连接工厂
 */
public class WeiXinConnectionFactory extends OAuth2ConnectionFactory<WeiXin> {

    /**
     *
     * @param providerId
     * @param appId
     * @param appSecret
     */
    public WeiXinConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WeiXinServiceProvider(appId, appSecret), new WeiXinAdaper());
    }

    /**
     * 	由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，
     * 	不用像QQ那样通过QQAdapter来获取
     * @param accessGrant
     * @return
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof WeiXinAccessGrant) {
            return ((WeiXinAccessGrant)accessGrant).getOpenId();
        }
        return null;
    }

    /**
     *
     * @param accessGrant
     * @return
     */
    public Connection<WeiXin> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<WeiXin>(super.getProviderId(), extractProviderUserId(accessGrant), 
        		accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(),
                accessGrant.getExpireTime(),
                getOAuth2ServiceProvider(),
                getApiAdapter(extractProviderUserId(accessGrant))
                );
    }

    /**
     *
     * @param data
     * @return
     */
    public Connection<WeiXin> createConnection(ConnectionData data) {
        return new OAuth2Connection<WeiXin>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    /**
     *
     * @param providerUserId
     * @return
     */
    private ApiAdapter<WeiXin> getApiAdapter(String providerUserId) {
        return new WeiXinAdaper(providerUserId);
    }

    /**
     *
     * @return
     */
    private OAuth2ServiceProvider<WeiXin> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WeiXin>) super.getServiceProvider();
    }


}
