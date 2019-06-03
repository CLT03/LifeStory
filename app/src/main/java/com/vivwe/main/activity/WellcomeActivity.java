package com.vivwe.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.mbs.sdk.net.HttpRequest;
import com.mbs.sdk.net.listener.OnResultListener;
import com.mbs.sdk.net.msg.WebMsg;
import com.shixing.sxvideoengine.License;
import com.vivwe.base.activity.BaseActivity;
import com.vivwe.base.cache.UserCache;
import com.vivwe.base.entity.UserToken;
import com.vivwe.main.R;
import com.vivwe.main.api.WebMainApi;
import com.vivwe.main.api.WebUserInfoApi;
import com.vivwe.main.entity.UserInfoEntity;


/**
 * ahtor: super_link
 * date: 2019/4/23 13:49
 * remark: 欢迎界面
 */
public class WellcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        final UserToken userToken = UserCache.Companion.getUserToken();
        UserInfoEntity userInfo = UserCache.Companion.getUserInfo();

//        SXVideo.shared().initLicense("FenLdZXrnW+f40bJcj2ivsQGAQfXKrzBUGybmzjmIDcwpj0rUs8aoIbRItJ4tGknq32D9f1b66nuFCiFraEoxftIgvu2DAenEs8pzzMXPtJg60xJ5dF2iWlzJWe2AYTKbkRkUdzLi3PmAFrxycOpwoS6qImrSwlrj39BsdoL5lAc2+Wr2KWyUOEkKZ1KuwvtDpl7Z394v85+4RLpAhh1Vem9rWIxr5qNAxmOjZTSdL2Z4K0NfCg0vdy6UseKtQya4nSk93KfZFjMLa2nqdbMN0drFn++u6cSblVIzo3a8ya1nqhhwrONfyQ+iGgKkqxA4Gewc6PedRShIpeYorBklQiExEi2yY7yqF2WtcAiFI9XXjAuu+dSnDfW1loNqCIaclD32oPkiXClK3ulvt2UT5ngrQ18KDS93LpSx4q1DJridKT3cp9kWMwtraep1sw3zhoeA7T8tg7QspjriD2grkkilnGME7Yq8yuYG6dYQiYnIkadb4FQX+aRvGmlV7dw9T9tz0273tN8q62EZdbsx4e1qOHPp6Oi00tXx83BZaHfsiMfbRvL8r29EU/PcxtmLvWrYmrejcflZiYtujUMlZ6+9uu7+kphK8kSXSfR0W1PuKK8hyvrpTro6mtRsYiOg3iI33M6Xv/xemLnfBzV59srUKMlgUuJqR0Vfd/3RMHJ3Mw8L3oxUU/flFdAeQ4ah7Wo4c+no6LTS1fHzcFloYWjXDy9iUfCUKU9taxYuxsjzbfHTQI/OinfMXCsD8oeycv9cLiDgcz62vHGhBAFLLJj6LUFBTJp8ce7cTQ0A0fG6iisGu02ML0taKjqk5w+CArbtkIMY8jpKE98WOM2HxV2D3XQNzr31Phm6EgP8ay1nqhhwrONfyQ+iGgKkqxA367o7342NYgFWzTgX/2FFAiExEi2yY7yqF2WtcAiFI/86kO3MqpKhsfSBfqGAFeEclD32oPkiXClK3ulvt2UT8bqKKwa7TYwvS1oqOqTnD4ICtu2QgxjyOkoT3xY4zYf/28pY7VfiPpEHgRRrbwDGEkilnGME7Yq8yuYG6dYQiYnIkadb4FQX+aRvGmlV7dwSYdeRYvABO2afLfzyzux4CsCbVB/gFLriPqiA2kbZq+0J8RUp9K5KWWFUFd2nTyPLvWrYmrejcflZiYtujUMlTERmtWIDj/XMWdRlcO2/jwXml4KBkSGeevwpBZDs2cefxKgkn7lGIt8nlIn6++BDdsrUKMlgUuJqR0Vfd/3RMGiMqLGsORC0r14xTIJAWC8KwJtUH+AUuuI+qIDaRtmrwoYGiYpb9jzKpkFYYyFea3mAeRF2Whcsd2LUhZzMfo4ycv9cLiDgcz62vHGhBAFLFhldX4EWhjl7KxQkrTJWLI=");
//        boolean licenseValid = SXVideo.shared().isLicenseValid();
//        android.widget.Toast.makeText(this, "license: " + licenseValid, android.widget.Toast.LENGTH_SHORT).show();

        String license = "FenLdZXrnW+f40bJcj2ivsQGAQfXKrzBUGybmzjmIDcwpj0rUs8aoIbRItJ4tGknq32D9f1b66nuFCiFraEoxftIgvu2DAenEs8pzzMXPtJg60xJ5dF2iWlzJWe2AYTKbkRkUdzLi3PmAFrxycOpwoS6qImrSwlrj39BsdoL5lAc2+Wr2KWyUOEkKZ1KuwvtDpl7Z394v85+4RLpAhh1Vem9rWIxr5qNAxmOjZTSdL2Z4K0NfCg0vdy6UseKtQya4nSk93KfZFjMLa2nqdbMN0drFn++u6cSblVIzo3a8ya1nqhhwrONfyQ+iGgKkqxA4Gewc6PedRShIpeYorBklQiExEi2yY7yqF2WtcAiFI9XXjAuu+dSnDfW1loNqCIaclD32oPkiXClK3ulvt2UT5ngrQ18KDS93LpSx4q1DJridKT3cp9kWMwtraep1sw3zhoeA7T8tg7QspjriD2grkkilnGME7Yq8yuYG6dYQiYnIkadb4FQX+aRvGmlV7dw9T9tz0273tN8q62EZdbsx4e1qOHPp6Oi00tXx83BZaHfsiMfbRvL8r29EU/PcxtmLvWrYmrejcflZiYtujUMlZ6+9uu7+kphK8kSXSfR0W1PuKK8hyvrpTro6mtRsYiOg3iI33M6Xv/xemLnfBzV59srUKMlgUuJqR0Vfd/3RMHJ3Mw8L3oxUU/flFdAeQ4ah7Wo4c+no6LTS1fHzcFloYWjXDy9iUfCUKU9taxYuxsjzbfHTQI/OinfMXCsD8oeycv9cLiDgcz62vHGhBAFLLJj6LUFBTJp8ce7cTQ0A0fG6iisGu02ML0taKjqk5w+CArbtkIMY8jpKE98WOM2HxV2D3XQNzr31Phm6EgP8ay1nqhhwrONfyQ+iGgKkqxA367o7342NYgFWzTgX/2FFAiExEi2yY7yqF2WtcAiFI/86kO3MqpKhsfSBfqGAFeEclD32oPkiXClK3ulvt2UT8bqKKwa7TYwvS1oqOqTnD4ICtu2QgxjyOkoT3xY4zYf/28pY7VfiPpEHgRRrbwDGEkilnGME7Yq8yuYG6dYQiYnIkadb4FQX+aRvGmlV7dwSYdeRYvABO2afLfzyzux4CsCbVB/gFLriPqiA2kbZq+0J8RUp9K5KWWFUFd2nTyPLvWrYmrejcflZiYtujUMlTERmtWIDj/XMWdRlcO2/jwXml4KBkSGeevwpBZDs2cefxKgkn7lGIt8nlIn6++BDdsrUKMlgUuJqR0Vfd/3RMGiMqLGsORC0r14xTIJAWC8KwJtUH+AUuuI+qIDaRtmrwoYGiYpb9jzKpkFYYyFea3mAeRF2Whcsd2LUhZzMfo4ycv9cLiDgcz62vHGhBAFLFhldX4EWhjl7KxQkrTJWLI=";
        License l = License.init(license);
        boolean b = l.isValid();
        Toast.makeText(this, "license: " + b, Toast.LENGTH_SHORT).show();

        // 用户已经登录，获取用户信息
        if(userToken != null && userInfo != null){

            HttpRequest.getInstance().excute(HttpRequest.create(WebMainApi.class).login(userToken.getAccount(), userToken.getPassword()), new OnResultListener() {
                @Override
                public void onWebUiResult(WebMsg webMsg) {

                    if (webMsg.dataIsSuccessed()) {
                        UserToken userToken1 = webMsg.getData(UserToken.class);
                        userToken.setToken(userToken1.getToken());
                        UserCache.Companion.setUserToken(userToken);
                    } else if (webMsg.netIsSuccessed()) {

                        Toast.makeText(WellcomeActivity.this, webMsg.getDesc(), Toast.LENGTH_SHORT).show();

                        toLoginActivity();
                        return;
                    }

                    HttpRequest.getInstance().excute(HttpRequest.create(WebUserInfoApi.class).getUserInfo(UserCache.Companion.getUserToken().getId()), new OnResultListener(){
                        @Override
                        public void onWebUiResult(WebMsg webMsg) {
                            if(webMsg.dataIsSuccessed()){
                                UserCache.Companion.setUserInfo(webMsg.getData(UserInfoEntity.class));
                            }

                            toMainActivity();
                        }
                    });

                }
            });
        } else { // 用户未登录，跳转到登录界面
            toLoginActivity();
        }
    }

    /**
     * 跳转登录
     */
    private void toLoginActivity(){
        Intent intent = new Intent();
        intent.setClass(WellcomeActivity.this, LoginActivity.class);
        WellcomeActivity.this.startActivity(intent);
        WellcomeActivity.this.finish();
    }

    /**
     * 跳转主页
     */
    private void toMainActivity(){
        Intent intent = new Intent();
        intent.setClass(WellcomeActivity.this, MainActivity.class);
        WellcomeActivity.this.startActivity(intent);
        WellcomeActivity.this.finish();
    }
}
