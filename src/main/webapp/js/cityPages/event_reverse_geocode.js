ymaps.ready(init);

function init() {
    var myPlacemark,
        myMap = new ymaps.Map('map', {
            center: [53.919690, 27.586894],
            zoom: 9
        }, {
            searchControlProvider: 'yandex#search'
        });

    // Слушаем клик на карте.
    myMap.events.add('click', function (e) {
        var coords = e.get('coords');

        // Если метка уже создана – просто передвигаем ее.
        if (myPlacemark) {
            myPlacemark.geometry.setCoordinates(coords);
        }
        // Если нет – создаем.
        else {
            myPlacemark = createPlacemark(coords);
            myMap.geoObjects.add(myPlacemark);
            // Слушаем событие окончания перетаскивания на метке.
            myPlacemark.events.add('dragend', function () {
                getAddress(myPlacemark.geometry.getCoordinates());
            });
        }
        getAddress(coords);
    });

    // Создание метки.
    function createPlacemark(coords) {
        return new ymaps.Placemark(coords, {
            iconCaption: 'поиск...'
        }, {
            preset: 'islands#violetDotIconWithCaption',
            draggable: true
        });
    }

    // Определяем адрес по координатам (обратное геокодирование).
    function getAddress(coords) {
        myPlacemark.properties.set('iconCaption', 'search...');
        ymaps.geocode(coords).then(function (res) {
            let firstGeoObject = res.geoObjects.get(0);
            coords = firstGeoObject.geometry.getCoordinates();
            // $('#name').value(firstGeoObject.getLocalities().join(', '));
            // console.log('\nКоординаты: %s', coords.toString());
            // console.log('\nГосударство: %s', firstGeoObject.getCountry());
            // console.log('Населенный пункт: %s', firstGeoObject.getLocalities().join(', ').split(',')[0]);
            // console.log('Адрес объекта: %s', firstGeoObject.getAddressLine());
            // console.log('Наименование здания: %s', firstGeoObject.getPremise() || '-');
            // console.log('Номер здания: %s', firstGeoObject.getPremiseNumber() || '-');
            $(function () {
                $('.countryName').val(firstGeoObject.getCountry());
                $('div.countryName').text(firstGeoObject.getCountry());
                $('.cityName').val(firstGeoObject.getLocalities().join(', ').split(',')[0]);
                $('div.cityName').text(firstGeoObject.getLocalities().join(', ').split(',')[0]);
            });

            myPlacemark.properties
                .set({
                    // Формируем строку с данными об объекте.
                    iconCaption: [
                        firstGeoObject.getCountry().concat(', ').concat(firstGeoObject.getLocalities().join(', ').split(',')[0])
                        // Название населенного пункта или вышестоящее административно-территориальное образование.
                        //firstGeoObject.getLocalities().length ? firstGeoObject.getLocalities() : firstGeoObject.getAdministrativeAreas(),
                        // Получаем путь до топонима, если метод вернул null, запрашиваем наименование здания.
                        //firstGeoObject.getThoroughfare() || firstGeoObject.getPremise()
                    ].filter(Boolean).join(', '),
                    // В качестве контента балуна задаем строку с адресом объекта.
                    balloonContent: firstGeoObject.getAddressLine()
                });
        });
    }
}

