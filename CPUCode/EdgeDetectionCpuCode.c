/**
 * Author: Bibrak Qamar Chandio
 * 		   PhD Student Indiana University, Bloomington, IN
 *		   10-Dec-2016
 *
 * Code Template from:
 * Document: MaxCompiler Training (maxcompiler-training.pdf)
 * Chapter: 2
 * Exercise Solution: 2
 *
 * Summary:
 * 	 Performs Difference of Gaussian EdgeDetection on an image.
 */
#include <math.h>
#include <stdio.h>
#include <stdlib.h>

#include "Maxfiles.h"
#include <MaxSLiCInterface.h>

#include "ppmIO.h"

int main(void)
{
	printf("Loading image.\n");
	int32_t *inImage;
	int width = 0, height = 0;
	loadImage("lena.ppm", &inImage, &width, &height, 0);

	int dataSize = width * height * sizeof(int);
	// Allocate a buffer for the output image
	int32_t *outImage = malloc(dataSize);

	printf("Running Kernel.\n");
	EdgeDetectionSolution(width * height, inImage, outImage);

	printf("Saving image.\n");
	writeImage("lena_edges.ppm", outImage, width, height, 0);

	printf("Exiting\n");
	return 0;
}
