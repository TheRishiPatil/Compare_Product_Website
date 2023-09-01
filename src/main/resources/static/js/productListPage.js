const selectedProducts = [];

function addToCompare(productUrl) {
	if (selectedProducts.length < 5) {
		selectedProducts.push(productUrl);
	}
}

function removeFromCompare(productUrl) {
	const index = selectedProducts.indexOf(productUrl);
	if (index !== -1) {
		selectedProducts.splice(index, 1);
	}
}

function compareSelected() {
	if (selectedProducts.length > 0) {
		window.location.href = `/compare-products?selectedProducts=${selectedProducts.join(',')}`;
	}
}