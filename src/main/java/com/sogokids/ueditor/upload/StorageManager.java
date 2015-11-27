package com.sogokids.ueditor.upload;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


import com.sogokids.ueditor.define.AppInfo;
import com.sogokids.ueditor.define.BaseState;
import com.sogokids.ueditor.define.State;
import com.sogokids.images.model.Images;
import com.sogokids.utils.util.FileUtil;
import com.sogokids.utils.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class StorageManager {
	public static final int BUFFER_SIZE = 8192;

	public StorageManager() {
	}

	public static State saveBinaryFile(byte[] data, String path) {
		File file = new File(path);

		State state = valid(file);

		if (!state.isSuccess()) {
			return state;
		}

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bos.write(data);
			bos.flush();
			bos.close();
		} catch (IOException ioe) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true, file.getAbsolutePath());
		state.putInfo( "size", data.length );
		state.putInfo( "title", file.getName() );
		return state;
	}

	public static State saveFileByInputStream(InputStream is, String path,
			long maxSize) {
		State state = null;

		File tmpFile = getTmpFile();

		byte[] dataBuf = new byte[ 2048 ];
		BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();

			if (tmpFile.length() > maxSize) {
				tmpFile.delete();
				return new BaseState(false, AppInfo.MAX_SIZE);
			}

			state = saveTmpFile(tmpFile, path);

			if (!state.isSuccess()) {
				tmpFile.delete();
			}

			return state;
			
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	public static State saveFileByInputStream(InputStream is, String path) {
		State state = null;

		File tmpFile = getTmpFile();

		byte[] dataBuf = new byte[ 2048 ];
		BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();

			state = saveTmpFile(tmpFile, path);

			if (!state.isSuccess()) {
				tmpFile.delete();
			}

			return state;
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static File getTmpFile() {
		File tmpDir = FileUtils.getTempDirectory();
		String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
		return new File(tmpDir, tmpFileName);
	}

	private static State saveTmpFile(File tmpFile, String path) {
		State state = null;
		File refile = null;
		Images images = new Images();

		try {

			sendHttpPost(FileUtil.UPLOAD_PATH,tmpFile,images);
			String path1 = FileUtil.SERVER_PATH + images.getUrl();

			refile = new File(path1);
			//FileUtils.moveFile(tmpFile, targetFile);
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true);
		state.putInfo( "size", refile.length());
		state.putInfo( "title", refile.getName().replace(".jpg",""));
//		System.out.print(images.getUrl());
		String s_url = images.getUrl().replace(".jpg", "_m.jpg");
//		System.out.print(s_url);
		state.putInfo( "url", s_url);
		
		return state;
	}

	/**
	 * httpClient发送图片上传数据到服务器
	 * @param upload_url
	 * @param file_n
	 * @param result
	 * @throws IOException
	 */
	private static void sendHttpPost(String upload_url, File file_n, Images result) throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(upload_url);
		FileBody bin = null;
		if (file_n != null) {
			bin = new FileBody(file_n);
		}

		MultipartEntity reqEntity = new MultipartEntity();
		reqEntity.addPart("file", bin);
		//reqEntity.addPart("cut", new StringBody("false", Charset.defaultCharset()));
		httpPost.setEntity(reqEntity);

		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new RuntimeException("fail to execute request: " + httpPost);
		}
		HttpEntity resEntity = response.getEntity();
		String entityStr = EntityUtils.toString(resEntity);
		Map<String, Object> map = StringUtil.parseJSON2Map(entityStr);
		if (map.get("errmsg").equals("success")) {
			Map<String, Object> map_data = StringUtil.parseJSON2Map(map.get("data").toString());
			result.setUrl(map_data.get("path").toString());
		} else {
			throw new RuntimeException("image upload failure:errorCode-" + map.get("errmsg"));
		}
	}

	private static State valid(File file) {
		File parentPath = file.getParentFile();

		if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
			return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}

		if (!parentPath.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}

		return new BaseState(true);
	}
}
