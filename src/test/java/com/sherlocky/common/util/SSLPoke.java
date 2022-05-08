package com.sherlocky.common.util;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

/**
 * Establish a SSL connection to a host and port, writes a byte and prints the response.
 * See http://confluence.atlassian.com/display/JIRA/Connecting+to+SSL+services
 * <ul>
 *     <li>使用命令：java SSLPoke www.baidu.com 443</li>
 *     <li>如果未配置classPath则需要使用命令：java -cp . SSLPoke www.baidu.com 443</li>
 * </ul>
 */
public class SSLPoke {

    public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: "+SSLPoke.class.getName()+" <host> <port>");
			System.exit(1);
		}
		try {
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(args[0], Integer.parseInt(args[1]));

			InputStream in = sslsocket.getInputStream();
			OutputStream out = sslsocket.getOutputStream();

			// Write a test byte to get a reaction :)
			out.write(1);

			while (in.available() > 0) {
				System.out.print(in.read());
			}
			System.out.println("Successfully connected");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}