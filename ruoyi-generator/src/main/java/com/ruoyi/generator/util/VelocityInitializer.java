package com.ruoyi.generator.util;

import java.util.Properties;
import org.apache.velocity.app.Velocity;
import com.ruoyi.common.constant.Constants;

/**
 * VelocityEngineファクトリー
 * 
 * @author ruoyi
 */
public class VelocityInitializer
{
    /**
     * VMメソッドの初期化
     */
    public static void initVelocity()
    {
        Properties p = new Properties();
        try
        {
            // classpathディレクトリ下のVMファイルをロード
            p.setProperty("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // 文字セットを定義
            p.setProperty(Velocity.INPUT_ENCODING, Constants.UTF8);
            // Velocityエンジンを初期化し、設定プロパティを指定
            Velocity.init(p);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
