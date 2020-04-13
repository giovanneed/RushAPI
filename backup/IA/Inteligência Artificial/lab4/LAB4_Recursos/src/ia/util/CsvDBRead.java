package ia.util;

import java.io.*;
import java.util.*;

/**
 * Read a CSV file to arrays. The last column of the CSV
 * file is used as target output, the other columns are the
 * inputs.  
 * 
 * @author  Alair Dias Júnior
 * @version 1.0
 */
public class CsvDBRead{
	
	private double m_inputs[][];
	private double m_outputs[];
	
	public class InvalidDBFileException extends Exception
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 663460948872276992L;
		
	}

	/** 
	 * Reads the CSV File
	 * @param file The file location
	 */
	public void readDB(String file) throws FileNotFoundException, IOException, InvalidDBFileException
	{
		RandomAccessFile RFile = new RandomAccessFile(file,"r");
		
		int cols = columnCount(RFile);
		int rows = rowCount(RFile);
		
		if (cols < 2 || rows == 0) throw new InvalidDBFileException();
		
		m_inputs  = new double[rows][cols-1];
		m_outputs = new double[rows];
		
		String line = null;
		int row = 0;
		while((line = RFile.readLine()) != null)
		{
			StringTokenizer st = new StringTokenizer(line,",");
			int col = 0;
			while (st.hasMoreTokens())
			{
				if (col < cols-1)
					m_inputs[row][col] = Double.parseDouble(st.nextToken());
				else
					m_outputs[row] = Double.parseDouble(st.nextToken());
				
				++col;
			}
			++row;
		}
		RFile.close();
	}

	/** 
	 * Returns the matrix of inputs read from the CSV file
	 * @return The matrix of inputs read from the CSV file
	 */
	public double[][] getInputs()
	{
		return m_inputs;
	}

	/** 
	 * Returns the array of outputs read from the CSV file
	 * @return The array of outputs read from the CSV file
	 */
	public double[] getOutputs()
	{
		return m_outputs;
	}
	
	private int columnCount(RandomAccessFile bufRdr) throws IOException
	{
		String line = bufRdr.readLine();
		if (line == null) return 0;
		int col = 0;
		StringTokenizer st = new StringTokenizer(line,",");
		while (st.hasMoreTokens())
		{
			st.nextToken();
			++col;
		}
		bufRdr.seek(0);
		return col;
	}
	
	private int rowCount(RandomAccessFile bufRdr) throws IOException
	{		
		int row = 0;
		while (bufRdr.readLine() != null)
		{
			++row;
		}
		bufRdr.seek(0);
		return row;
	}
}

