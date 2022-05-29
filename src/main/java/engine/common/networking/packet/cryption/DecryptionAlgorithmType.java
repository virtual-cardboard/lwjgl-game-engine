package engine.common.networking.packet.cryption;

public enum DecryptionAlgorithmType {

	CLIENT_PUBLIC_DECRYPT(EncryptionAlgorithmType.CLIENT_PUBLIC_ENCRYPT),
	CLIENT_PRIVATE_DECRYPT(EncryptionAlgorithmType.CLIENT_PRIVATE_ENCRYPT),
	SERVER_PUBLIC_DECRYPT(EncryptionAlgorithmType.SERVER_PUBLIC_ENCRYPT),
	SERVER_PRIVATE_DECRYPT(EncryptionAlgorithmType.SERVER_PRIVATE_ENCRYPT),
	AES_PUBLIC_DECRYPT(EncryptionAlgorithmType.AES_PUBLIC_ENCRYPT),
	AES_CTR_PUBLIC_DECRYPT(EncryptionAlgorithmType.AES_CTR_PUBLIC_ENCRYPT);

	private EncryptionAlgorithmType inverse;

	private DecryptionAlgorithmType(EncryptionAlgorithmType inverse) {
		this.inverse = inverse;
	}

	public EncryptionAlgorithmType inverse() {
		return inverse;
	}

}
