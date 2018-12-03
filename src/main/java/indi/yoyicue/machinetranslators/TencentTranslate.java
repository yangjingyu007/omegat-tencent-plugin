package indi.yoyicue.machinetranslators;


import java.awt.Window;
import java.util.TreeMap;
import javax.swing.JComboBox;
import java.awt.Component;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.tmt.v20180321.TmtClient;

import com.tencentcloudapi.tmt.v20180321.models.TextTranslateRequest;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateResponse;


import org.omegat.core.Core;
import org.omegat.core.machinetranslators.BaseTranslate;
import org.omegat.gui.exttrans.MTConfigDialog;
import org.omegat.util.Language;



public class TencentTranslate extends BaseTranslate {

    protected static final String PROPERTY_API_SECRET_ID = "tencent.api.secret.Id";
    protected static final String PROPERTY_API_SECRET_KEY = "tencent.api.secret.Key";
	protected static final String PROPERTY_API_SECRET_REGION = "tencent.api.secret.Region";	
    
	public static final String[] sItems = {
		"华北地区(北京)",
		"西南地区(成都)",
         "西南地区(重庆)", 
         "华东地区(上海)", 		 
		 "华南地区(广州)", 
		 "华南地区(广州Open)", 
		 "东南亚地区(香港)", 
		"东南亚地区(新加坡)",	
		"亚太地区(孟买)",	
		"亚太地区(首尔)", 
		"亚太地区(曼谷)",
		"亚太地区(东京)", 
		"欧洲地区(法兰克福)",		
		"欧洲地区(莫斯科)", 		 
		"美国西部(硅谷)", 			 
		"美国东部(弗吉尼亚)", 	
		"北美地区(多伦多)",
		"华东地区(上海金融)", 
	"华南地区(深圳金融)"};
	
	public static final String[] sValues = {
			//"华北地区(北京)"
		"ap-beijing"    
         //"西南地区(成都)"
		 ,"ap-chengdu"    
         //"西南地区(重庆)"
		 ,"ap-chongqing"    
         //"华东地区(上海)"
		 ,"ap-shanghai" 
		 //"华南地区(广州)"
		 ,"ap-guangzhou" 
		 //"华南地区(广州Open)"
		 ,"ap-guangzhou-open" 
		 //"东南亚地区(香港)"
		 ,"ap-hongkong" 
		//"东南亚地区(新加坡)"
		, "ap-singapore" 		
		//"亚太地区(孟买)"
		, "ap-mumbai" 	
		//"亚太地区(首尔)"
		,"ap-seoul" 
		//"亚太地区(曼谷)"
		,"ap-bangkok" 	
		//"亚太地区(东京)"
		,"ap-tokyo" 	
		//"欧洲地区(法兰克福)"
		,"eu-frankfurt" 		
		//"欧洲地区(莫斯科)"
		,"eu-moscow" 			 
		//"美国西部(硅谷)"
		,"na-siliconvalley" 			 
		//"美国东部(弗吉尼亚)"
		,"na-ashburn"		
		//"北美地区(多伦多)"
		, "na-toronto"		 
		//"华东地区(上海金融)"
		, "ap-shanghai-fsi"
		//"华南地区(深圳金融)"
		, "ap-shenzhen-fsi"
		};

	public static void loadPlugins() {
        Core.registerMachineTranslationClass(TencentTranslate.class);
	
    }

    public static void unloadPlugins() {
    }

    @Override
    protected String getPreferenceName() {
        return "allow_tencent_translate";
    }

    public String getName() {
        return "Tencent Translate";
    }

    @Override
    protected String translate(Language sLang, Language tLang, String text) throws Exception {

        String SecretId = getCredential(PROPERTY_API_SECRET_ID);
        String SecretKey = getCredential(PROPERTY_API_SECRET_KEY);
     	String SecrctRegion = getCredential(PROPERTY_API_SECRET_REGION);
		
		
		String translation;
		

		
		 try{
				
				
            Credential cred = new Credential(SecretId, SecretKey,"OmegaT");
            
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tmt.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);            
            
            TmtClient client = new TmtClient(cred, SecrctRegion.equals("") ?"ap-beijing":SecrctRegion, clientProfile);
            
			
			String sourcelang = tmtLang(sLang);
			String targetlang = tmtLang(tLang);
            
			text = text.replace('\"',' ');
			
			String params = "{\"SourceText\":\""+text+"\",\"Source\":\""+sourcelang+"\",\"Target\":\""+targetlang+"\",\"ProjectId\":\"101\"}";
			
			
			
           TextTranslateRequest req = TextTranslateRequest.fromJsonString(params, TextTranslateRequest.class);
            
           TextTranslateResponse resp = client.TextTranslate(req);
     			
			
			TextTranslateRequest.toJsonString(resp);
			translation = resp.getTargetText();
		            
        }catch (java.lang.Exception e){
                translation = "Exception :" + e.toString();
        }
        return translation;
   
	}

    private String tmtLang(Language language) {
        String lang = language.getLanguage();
        if (lang.equalsIgnoreCase("zh-cn") || lang.equalsIgnoreCase("zh-hk") || lang.equalsIgnoreCase("zh-tw")) {
            return "zh";
        } else if (lang.equalsIgnoreCase("ja")) {
            return "jp";
        } else if (lang.equalsIgnoreCase("ko")) {
            return "kr";
        } else {
            return language.getLanguageCode().toLowerCase();
        }
    }
    
    @Override
    public boolean isConfigurable() {
        return true;
    }
    
    @Override
    public void showConfigurationUI(Window parent) {
	
        MTConfigDialog dialog = new MTConfigDialog(parent, getName()) {
            @Override
            protected void onConfirm() {
                String id = panel.valueField1.getText().trim();
                String key = panel.valueField2.getText().trim();
                boolean temporary = panel.temporaryCheckBox.isSelected();
                setCredential(PROPERTY_API_SECRET_ID, id, temporary);
                setCredential(PROPERTY_API_SECRET_KEY, key, temporary);
            
				Component[] components = panel.itemsPanel.getComponents();
				for (int i=0;i< components.length;i++)
				{
					if(components[i] instanceof JComboBox)
					{
						JComboBox combox1 = (JComboBox)components[i];
						int iSelect = combox1.getSelectedIndex();
						setCredential(PROPERTY_API_SECRET_REGION, sValues[iSelect], temporary);
						return;
					}
				
				}
				
				setCredential(PROPERTY_API_SECRET_REGION, sValues[0], temporary);
			}
        };
		
		javax.swing.JComboBox combox1 = new javax.swing.JComboBox(TencentTranslate.sItems);

		
		String valueSeclect = getCredential(PROPERTY_API_SECRET_REGION);
		
		if(valueSeclect.equals(""))
		{
			combox1.setSelectedIndex(0); 
		}
		else
		{
			for(int i =0;i < TencentTranslate.sValues.length;i++)
			{
				if(valueSeclect.equals(TencentTranslate.sValues[i]))
				{
					combox1.setSelectedIndex(i);
					break;
				}
			}
		}
		
		dialog.panel.itemsPanel.setLayout(new java.awt.FlowLayout());
		dialog.panel.itemsPanel.add(new javax.swing.JLabel("Select Region："));
		dialog.panel.itemsPanel.add(combox1);
		
		
        dialog.panel.valueLabel1.setText("secretId");
        dialog.panel.valueField1.setText(getCredential(PROPERTY_API_SECRET_ID));
        dialog.panel.valueLabel2.setText("secretKey");
        dialog.panel.valueField2.setText(getCredential(PROPERTY_API_SECRET_KEY));
        dialog.panel.temporaryCheckBox.setSelected(isCredentialStoredTemporarily(PROPERTY_API_SECRET_ID));
        dialog.panel.temporaryCheckBox.setSelected(isCredentialStoredTemporarily(PROPERTY_API_SECRET_KEY));
        dialog.show();
    }

}

/*
		<option value="ap-beijing">华北地区(北京)</option>
			<option value="ap-chengdu">西南地区(成都)</option>			
			<option value="ap-chongqing">西南地区(重庆)</option>			
			<option value="ap-shanghai">华东地区(上海)</option>
			<option value="ap-guangzhou">华南地区(广州)</option>
			<option value="ap-guangzhou-open">华南地区(广州Open)</option>
			<option value="ap-hongkong">东南亚地区(香港)</option>
			<option value="ap-singapore">东南亚地区(新加坡)</option>
			<option value="ap-mumbai">亚太地区(孟买)</option>
			<option value="ap-seoul">亚太地区(首尔)</option>	
			<option value="ap-bangkok">亚太地区(曼谷)</option>		
			<option value="ap-tokyo">亚太地区(东京)</option>		
			<option value="eu-frankfurt">欧洲地区(法兰克福)</option>
			<option value="eu-moscow">欧洲地区(莫斯科)</option>
			<option value="na-siliconvalley">美国西部(硅谷)</option>
			<option value="na-ashburn">美国东部(弗吉尼亚)</option>
			<option value="na-toronto">北美地区(多伦多)</option>


			<option disabled="disabled">______金融区______</option>
			<option value="ap-shanghai-fsi">华东地区(上海金融)</option>
			<option value="ap-shenzhen-fsi">华南地区(深圳金融)</option>
		*/
		