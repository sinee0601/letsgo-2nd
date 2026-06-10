
function openPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            document.getElementById('addr1').value = data.roadAddress || data.address;
            document.getElementById('mapx').value = '126.9784';
            document.getElementById('mapy').value = '37.5665';
        }
    }).open();
}




