package context.input.networking.packet.cryption;

import static context.input.networking.packet.cryption.DecryptionAlgorithmType.AES_CTR_PUBLIC_DECRYPT;
import static context.input.networking.packet.cryption.DecryptionAlgorithmType.AES_PUBLIC_DECRYPT;
import static context.input.networking.packet.cryption.DecryptionAlgorithmType.CLIENT_PRIVATE_DECRYPT;
import static context.input.networking.packet.cryption.DecryptionAlgorithmType.CLIENT_PUBLIC_DECRYPT;
import static context.input.networking.packet.cryption.DecryptionAlgorithmType.SERVER_PRIVATE_DECRYPT;
import static context.input.networking.packet.cryption.DecryptionAlgorithmType.SERVER_PUBLIC_DECRYPT;

public enum EncryptionAlgorithmType {

	CLIENT_PUBLIC_ENCRYPT(CLIENT_PUBLIC_DECRYPT),
	CLIENT_PRIVATE_ENCRYPT(CLIENT_PRIVATE_DECRYPT),
	SERVER_PUBLIC_ENCRYPT(SERVER_PUBLIC_DECRYPT),
	SERVER_PRIVATE_ENCRYPT(SERVER_PRIVATE_DECRYPT),
	AES_PUBLIC_ENCRYPT(AES_PUBLIC_DECRYPT),
	AES_CTR_PUBLIC_ENCRYPT(AES_CTR_PUBLIC_DECRYPT);

	private DecryptionAlgorithmType inverse;

	private EncryptionAlgorithmType(DecryptionAlgorithmType inverse) {
		this.inverse = inverse;
	}

	public DecryptionAlgorithmType inverse() {
		return inverse;
	}

}
