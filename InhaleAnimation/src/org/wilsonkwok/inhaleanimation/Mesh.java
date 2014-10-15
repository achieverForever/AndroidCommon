package org.wilsonkwok.inhaleanimation;

/**
 * A Mesh that has (rows+1) x (columns+1) vertices.
 */
public abstract class Mesh {
	
	protected int rows;
	protected int columns;
	
	protected float[] verts;
	
	public Mesh(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		verts = new float[(rows + 1) * (columns + 1) * 2];
	}
	
	public float[] getVertices() {
		return verts;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public void buildMesh(float width, float height) {
		float dx = width / columns;
		float dy = height / rows;
		
		int index = 0;
		
		for (int i = 0; i < rows + 1; i++) {
			for (int j = 0; j < columns + 1; j++) {
				verts[index] = j * dx;
				verts[index + 1] = i * dy;
				index += 2;
			}
		}
	}

}
