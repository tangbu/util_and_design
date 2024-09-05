package com.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.io.FileUtils;

public class FileHandleMain {
    public static final String MODE_ENCRYPT = "e";
    public static final String MODE_DECRYPT = "d";
    public static final String MODE_CLEAR = "c";

    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        String mode = getMode();
        File file = getFile();
        String secret = "";
        if (!Objects.equals(mode, MODE_CLEAR)) {
            secret = getSecret();
        }
        handleFile(mode, secret, file);

    }

    private static void handleFile(String mode, String secret, File file) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        if (Objects.equals(mode, MODE_ENCRYPT)) {
            encryptFile(secret, file);
        } else if (Objects.equals(mode, MODE_DECRYPT)) {
            decryptFile(secret, file);
        } else if (Objects.equals(mode, MODE_CLEAR)){
            handleClearFile(file);
        }
    }

    private static void handleClearFile(File file) throws IOException {
        if (file.isDirectory()) {
            Collection<File> files = FileUtils.listFiles(file, null, true);
            for (File subFile : files) {
                if (!subFile.isDirectory()) {
                    if (isTargetFile(subFile.getCanonicalPath(),"")){
                        FileUtils.deleteQuietly(subFile);
                    }
                }
            }
        } else if (file.isFile()) {
            if (isTargetFile(file.getCanonicalPath(),"")){
                FileUtils.deleteQuietly(file);
            }
        }
    }

    private static void decryptFile(String secret, File file) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if (file.isDirectory()) {
            Collection<File> files = FileUtils.listFiles(file, null, true);
            for (File subFile : files) {
                if (!subFile.isDirectory()) {
                    if (isTargetFile(subFile.getCanonicalPath(),"-e")){
                        FileEncryptor.decryptedFile(new FileInputStream(subFile), new FileOutputStream(getFileModeName(subFile.getCanonicalPath(), "")), secret);
                    }
                }
            }
        } else if (file.isFile()) {
            FileEncryptor.decryptedFile(new FileInputStream(file), new FileOutputStream(getFileModeName(file.getCanonicalPath(), "")), secret);
        }
    }

    private static String getFileModeName(String fileName, String modeSuffix) {
        String[] split = fileName.split("\\.");
        String name = split[0];
        
        String suffix = split[1];

        if (Objects.equals(modeSuffix, "-e")) {
            if (name.endsWith("-e")) {
                return fileName;
            } else {
                return name + "-e." + suffix;
            }
        } else if (Objects.equals(modeSuffix, "-d")) {
            if (name.endsWith("-d")) {
                return fileName;
            } else {
                return name + "-d." + suffix;
            }

        } else if (Objects.equals(modeSuffix, "")) {
            String removeD = StringUtils.removeEnd(name, "-d");
            String removeED = StringUtils.removeEnd(removeD, "-e");
            return removeED + "." + suffix;
        }
        throw new RuntimeException();
    }


    private static void encryptFile(String secret, File file) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if (file.isDirectory()) {
            Collection<File> files = FileUtils.listFiles(file, null, true);
            for (File subFile : files) {
                if (!subFile.isDirectory()) {
                    if (isTargetFile(subFile.getCanonicalPath(),"")){
                        FileEncryptor.encryptFile(new FileInputStream(subFile), new FileOutputStream(getFileModeName(subFile.getCanonicalPath(), "-e")), secret);
                    }
                }
            }
        } else if (file.isFile()) {
            FileEncryptor.encryptFile(new FileInputStream(file), new FileOutputStream(getFileModeName(file.getCanonicalPath(), "-e")), secret);
        }
    }
    
    private static boolean isTargetFile(String fileName, String modeSuffix){
        String[] split = fileName.split("\\.");
        String name = split[0];

        if (Objects.equals(modeSuffix, "-e")) {
            return name.endsWith("-e");
        } else if (Objects.equals(modeSuffix, "-d")) {
            return name.endsWith("-d");
        } else if (Objects.equals(modeSuffix, "")){
            return !name.endsWith("-e") && !name.endsWith("-d");
        }
        throw new RuntimeException();
    }

    private static File getFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入文件夹或文件路径:");
        String file = scanner.nextLine();
        return new File(file);
    }

    private static String getMode() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入模式: e/d/c:");
        // 读取用户输入的字符串
        String mode = scanner.nextLine();
        return mode;
    }

    private static String getSecret() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入密钥: ");
        // 读取用户输入的字符串
        String secret = scanner.nextLine();
        scanner.close();
        return secret;
    }
}
