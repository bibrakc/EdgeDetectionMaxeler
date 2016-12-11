/**
* Author: Bibrak Qamar Chandio
 * 		   PhD Student Indiana University, Bloomington, IN
 *		   10-Dec-2016
 * Summary:
 * 	 Kernel that performs a Difference of Gaussian edge detection
 *   on an image. The image is a loaded and send to DFE as a
 *   color image and the output is also color.
 */
package edgedetection;

import com.maxeler.maxcompiler.v2.kernelcompiler.Kernel;
import com.maxeler.maxcompiler.v2.kernelcompiler.KernelParameters;
import com.maxeler.maxcompiler.v2.kernelcompiler.types.base.DFEVar;

class EdgeDetectionSolutionKernel extends Kernel {

	protected EdgeDetectionSolutionKernel(KernelParameters parameters) {
		super(parameters);

		int height = 256, width = 256;

		DFEVar inImage = io.input("inImage", dfeInt(32));
		DFEVar R = inImage >> 16;
		DFEVar G = (inImage >> 8) & 0xFF;
		DFEVar B = inImage & 0xFF;
		
		R -= 1;
		G -= 1;
		B -= 1;
		
		DFEVar window[][] = new DFEVar[3][3]; // x, y

		for (int x = -1; x <= 1; x++)
			for (int y = -1; y <= 1; y++)
				window[x + 1][y + 1] = stream.offset(inImage, y * width + x);

		DFEVar sub1 = constant.var(0);
		DFEVar sub2 = constant.var(0);
		DFEVar sub3 = constant.var(0);
		DFEVar sub4 = constant.var(0);
		
		/*
		sub1 = window[0][0] - window[2][2];
		sub2 = window[2][0] - window[0][2];
		sub3 = window[1][0] - window[1][2];
		sub4 = window[0][1] - window[2][1];
		*/
		
		DFEVar R_1 = window[0][0] >> 16;
		DFEVar G_1 = (window[0][0] >> 8) & 0xFF;
		DFEVar B_1 = window[0][0] & 0xFF;
		
		DFEVar R_2 = window[2][2] >> 16;
		DFEVar G_2 = (window[2][2] >> 8) & 0xFF;
		DFEVar B_2 = window[2][2] & 0xFF;
		
		DFEVar sub_R = R_1 - R_2;
		DFEVar sub_G = G_1 - G_2;
		DFEVar sub_B = B_1 - B_2;


		DFEVar absSub1_R = sub_R >= 0 ? sub_R : -sub_R;
		DFEVar absSub1_G = sub_G >= 0 ? sub_G : -sub_G;
		DFEVar absSub1_B = sub_B >= 0 ? sub_B : -sub_B;
		
//
		R_1 = window[2][0] >> 16;
		G_1 = (window[2][0] >> 8) & 0xFF;
		B_1 = window[2][0] & 0xFF;
		
		R_2 = window[0][2] >> 16;
		G_2 = (window[0][2] >> 8) & 0xFF;
		B_2 = window[0][2] & 0xFF;
		
		sub_R = R_1 - R_2;
		sub_G = G_1 - G_2;
		sub_B = B_1 - B_2;


		DFEVar absSub2_R = sub_R >= 0 ? sub_R : -sub_R;
		DFEVar absSub2_G = sub_G >= 0 ? sub_G : -sub_G;
		DFEVar absSub2_B = sub_B >= 0 ? sub_B : -sub_B;
//
		R_1 = window[1][0] >> 16;
		G_1 = (window[1][0] >> 8) & 0xFF;
		B_1 = window[1][0] & 0xFF;
		
		R_2 = window[1][2] >> 16;
		G_2 = (window[1][2] >> 8) & 0xFF;
		B_2 = window[1][2] & 0xFF;
		
		sub_R = R_1 - R_2;
		sub_G = G_1 - G_2;
		sub_B = B_1 - B_2;


		DFEVar absSub3_R = sub_R >= 0 ? sub_R : -sub_R;
		DFEVar absSub3_G = sub_G >= 0 ? sub_G : -sub_G;
		DFEVar absSub3_B = sub_B >= 0 ? sub_B : -sub_B;
//
		R_1 = window[0][1] >> 16;
		G_1 = (window[0][1] >> 8) & 0xFF;
		B_1 = window[0][1] & 0xFF;
		
		R_2 = window[2][1] >> 16;
		G_2 = (window[2][1] >> 8) & 0xFF;
		B_2 = window[2][1] & 0xFF;
		
		sub_R = R_1 - R_2;
		sub_G = G_1 - G_2;
		sub_B = B_1 - B_2;


		DFEVar absSub4_R = sub_R >= 0 ? sub_R : -sub_R;
		DFEVar absSub4_G = sub_G >= 0 ? sub_G : -sub_G;
		DFEVar absSub4_B = sub_B >= 0 ? sub_B : -sub_B;

//
		DFEVar max_sub1_sub2_R = (absSub1_R > absSub2_R) ? absSub1_R : absSub2_R;
		DFEVar max_sub1_sub2_G = (absSub1_G > absSub2_G) ? absSub1_G : absSub2_G;
	    DFEVar max_sub1_sub2_B = (absSub1_B > absSub2_B) ? absSub1_B : absSub2_B;

		DFEVar max_sub3_sub4_R = (absSub3_R > absSub4_R) ? absSub3_R : absSub4_R;
		DFEVar max_sub3_sub4_G = (absSub3_G > absSub4_G) ? absSub3_G : absSub4_G;
	    DFEVar max_sub3_sub4_B = (absSub3_B > absSub4_B) ? absSub3_B : absSub4_B;
		
		DFEVar result_R = (max_sub1_sub2_R > max_sub3_sub4_R) ? max_sub1_sub2_R : max_sub3_sub4_R;
		DFEVar result_G = (max_sub1_sub2_G > max_sub3_sub4_G) ? max_sub1_sub2_G : max_sub3_sub4_G;
		DFEVar result_B = (max_sub1_sub2_B > max_sub3_sub4_B) ? max_sub1_sub2_B : max_sub3_sub4_B;
		
		result_R = (result_R << 8) | (result_G & 0xFF);
		result_R = (result_R << 8) | (result_R & 0xFF);
		io.output("outImage", result_R, result_R.getType());

	}
}
