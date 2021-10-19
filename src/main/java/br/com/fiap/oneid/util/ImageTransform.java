package br.com.fiap.oneid.util;

import br.com.fiap.oneid.model.Usuario;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class ImageTransform {
	static final String SUFFIX_IMG = ".jpg";
	public static final String PATH_LOCAL_IMG = "src/main/resources/static/img/usuarios/";
	static final File index = new File(PATH_LOCAL_IMG);

	public static void createMapAndImgPushView(Map<String, Usuario> mapeamento, List<Usuario> usuarios)
			throws IOException {
		for (Usuario usuario : usuarios) {
			if (usuario.getFotoPerfil() != null) {
				String namespaceURI = String.valueOf(usuario.getIdUsuario());
				File temp = new File(PATH_LOCAL_IMG + namespaceURI + SUFFIX_IMG);
				writesImageInTemp(usuario, temp);
				mapeamento.put(namespaceURI, usuario);
			} else {
				mapeamento.put("../show/perfil.jpg", usuario);
			}
		}
	}

	public static void verifyIfExistsImgs() {
		if (index.exists()) {
			String[] entries = index.list();
			for (String s : Objects.requireNonNull(entries)) {
				File currentFile = new File(index.getPath(), s);
				currentFile.delete();
			}
		}
	}

	private static void writesImageInTemp(Usuario usuario, File temp) throws IOException {
		FileOutputStream fos;
		FileDescriptor fd;
		fos = new FileOutputStream(temp.getAbsolutePath());
		fos.write(usuario.getFotoPerfil());
		fd = fos.getFD();
		fos.flush();
		fd.sync();
		fos.close();
	}

	public static byte[] returnBytesDefault() throws IOException {
		File file = new File("src/main/resources/static/show/perfil.jpg");
		return Files.readAllBytes(file.toPath());
	}
}
