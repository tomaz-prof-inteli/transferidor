package tms.transferidor.util;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SenhaUtil {

	// gera o hash com salt da senha
	public static String hash(String senha) throws Exception {
        // Gera salt
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        // Gera hash usando salt
        KeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        // Combina salt + hash e codifica em base64 para armazenamento
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);
        return saltBase64 + ":" + hashBase64;		
	}
	
	public static boolean validar(String senha, String hashComSalt) {
        String[] parts = hashComSalt.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] hashOriginal = Base64.getDecoder().decode(parts[1]);

        KeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, 65536, 128);
        try {
        	SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        	byte[] hashTeste = factory.generateSecret(spec).getEncoded();

	        // Compara os hashes
	        if (hashOriginal.length != hashTeste.length) return false;
	        for (int i = 0; i < hashOriginal.length; i++) {
	            if (hashOriginal[i] != hashTeste[i]) return false;
	        }
        } catch(Exception ex) {
        	throw new AssertionError();
        }
        return true;
	}
}
