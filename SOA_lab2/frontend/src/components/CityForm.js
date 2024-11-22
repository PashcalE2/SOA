import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {Link, useLocation, useParams} from 'react-router-dom';
process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

function CityForm() {
    const defaultCity = {
        id: null,
        name: '',
        coordinates: { x: '', y: '' },
        area: '',
        population: '',
        metersAboveSeaLevel: '',
        establishmentDate: '',
        telephoneCode: '',
        climate: 'TROPICAL_SAVANNA', // Default climate
        governor: { name: '', age: '', height: '' },
    };
    const cityId = useLocation().state?.cityId;
    const [city, setCity] = useState(defaultCity);
    const [climates] = useState([
        'TROPICAL_SAVANNA',
        'HUMIDCONTINENTAL',
        'SUBARCTIC',
        'POLAR_ICECAP',
        'DESERT',
    ]);
    const [errors, setErrors] = useState({});

    const handleXmlError = (xmlData) => {
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(xmlData, "application/xml");
        const errorNode = xmlDoc.getElementsByTagName("error")[0];
        if (errorNode) {
            const messageNode = errorNode.getElementsByTagName("message")[0];
            if (messageNode && messageNode.textContent) {
                console.log(`Error: ${messageNode.textContent}`)
                return messageNode.textContent;
            }
        }
        return false;
    };

    useEffect(() => {
        if (cityId) {
            loadCity(cityId).catch((err) => {
                console.error("Error in fetching city:", err);
            });
        }
    }, [cityId]);

    // Функция для парсинга XML данных
    const parseXMLResponse = (xml) => {
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(xml, 'text/xml');

        const cityElement = xmlDoc.getElementsByTagName('city')[0];
        if (!cityElement) {
            throw new Error('Invalid XML structure');
        }

        const name = cityElement.getElementsByTagName('name')[0]?.textContent || '';
        const coordinates = {
            x: cityElement.getElementsByTagName('coordinates')[0]?.getElementsByTagName('x')[0]?.textContent || '',
            y: cityElement.getElementsByTagName('coordinates')[0]?.getElementsByTagName('y')[0]?.textContent || '',
        };
        const area = cityElement.getElementsByTagName('area')[0]?.textContent || '';
        const population = cityElement.getElementsByTagName('population')[0]?.textContent || '';
        const metersAboveSeaLevel = cityElement.getElementsByTagName('metersAboveSeaLevel')[0]?.textContent || '';
        const establishmentDate = cityElement.getElementsByTagName('establishmentDate')[0]?.textContent || '';
        const telephoneCode = cityElement.getElementsByTagName('telephoneCode')[0]?.textContent || '';
        const climate = cityElement.getElementsByTagName('climate')[0]?.textContent || '';

        const governorElement = cityElement.getElementsByTagName('governor')[0];
        const governor = {
            name: governorElement?.getElementsByTagName('name')[0]?.textContent || '',
            age: governorElement?.getElementsByTagName('age')[0]?.textContent || '',
            height: governorElement?.getElementsByTagName('height')[0]?.textContent || '',
        };

        return {
            name,
            coordinates,
            area,
            population,
            metersAboveSeaLevel,
            establishmentDate,
            telephoneCode,
            climate,
            governor,
        };
    };

    const loadCity = async (id) => {
        try {
            const response = await axios.get(`https://localhost:22601/cities/${id}`, {
                headers: {
                    'Accept': 'application/xml',
                    'Content-Type': 'application/xml',
                }
            });
            const cityData = parseXMLResponse(response.data);
            setCity(cityData);
        } catch (err) {
            const errorMessage = err.response?.data
                ? handleXmlError(err.response.data)
                : null;

            if (errorMessage) {
                alert(`Error fetching city: ${err.message}\n${errorMessage}`);
                return;
            }
            alert(`Error fetching city: ${err.message}`);
            console.error("Error fetching city: ", err);
        }
    };

    const validateFields = () => {
        const newErrors = {};
        let isValid = true;

        if (!city.name) {
            newErrors.name = 'Name is required.';
            isValid = false;
        }
        if (city.coordinates.x === '') {
            newErrors.coordinatesX = 'Coordinates X is required.';
            isValid = false;
        }
        if (city.coordinates.y === '') {
            newErrors.coordinatesY = 'Coordinates Y is required.';
            isValid = false;
        }
        if (city.area <= 0) {
            newErrors.area = 'Area must be greater than 0.';
            isValid = false;
        }
        if (city.population <= 0) {
            newErrors.population = 'Population must be greater than 0.';
            isValid = false;
        }
        if (city.telephoneCode <= 0 || city.telephoneCode > 100000) {
            newErrors.telephoneCode = 'Telephone Code must be greater than 0 and less than or equal to 100000.';
            isValid = false;
        }
        if (!city.governor.name) {
            newErrors.governorName = 'Governor Name is required.';
            isValid = false;
        }
        if (city.governor.age <= 0) {
            newErrors.governorAge = 'Governor Age must be greater than 0.';
            isValid = false;
        }
        if (city.governor.height <= 0) {
            newErrors.governorHeight = 'Governor Height must be greater than 0.';
            isValid = false;
        }

        setErrors(newErrors);
        return isValid;
    };

    const submitForm = async (e) => {
        e.preventDefault();

        if (!validateFields()) return;

        const url = cityId
            ? `https://localhost:22601/cities/${cityId}`
            : `https://localhost:22601/cities`;
        const method = cityId ? 'put' : 'post';

        const xmlData = `
            <city>
                <name>${city.name}</name>
                <coordinates>
                    <x>${city.coordinates.x}</x>
                    <y>${city.coordinates.y}</y>
                </coordinates>
                <area>${city.area}</area>
                <population>${city.population}</population>
                <metersAboveSeaLevel>${city.metersAboveSeaLevel}</metersAboveSeaLevel>
                <establishmentDate>${city.establishmentDate}</establishmentDate>
                <telephoneCode>${city.telephoneCode}</telephoneCode>
                <climate>${city.climate}</climate>
                <governor>
                    <name>${city.governor.name}</name>
                    <age>${city.governor.age}</age>
                    <height>${city.governor.height}</height>
                </governor>
            </city>`;

        try {
            await axios[method](url, xmlData, {
                headers: {
                    'Accept': 'application/xml',
                    'Content-Type': 'application/xml',
                }
            });
            alert('City saved successfully!');
        } catch (err) {
            const errorMessage = err.response?.data
                ? handleXmlError(err.response.data)
                : null;

            if (errorMessage) {
                alert(`Error saving city: ${err.message}\n${errorMessage}`);
                return;
            }

            alert(`Error saving city: ${err.message}`);
            console.error("Error saving city: ", err);
        }
    };

    return (
        <div>
            <h1>{cityId ? 'Edit City' : 'Add City'}</h1>
            <form onSubmit={submitForm}>
                <div>
                    <label>Name*:</label>
                    <input
                        type="text"
                        value={city.name}
                        onChange={(e) => setCity({ ...city, name: e.target.value })}
                        required
                    />
                    {errors.name && <span className="error">{errors.name}</span>}
                </div>
                <div>
                    <label>Coordinates X*:</label>
                    <input
                        type="number"
                        value={city.coordinates.x || ''}
                        onChange={(e) =>
                            setCity({
                                ...city,
                                coordinates: { ...city.coordinates, x: e.target.value },
                            })
                        }
                        required
                    />
                    {errors.coordinatesX && <span className="error">{errors.coordinatesX}</span>}
                </div>
                <div>
                    <label>Coordinates Y*:</label>
                    <input
                        type="number"
                        value={city.coordinates.y || ''}
                        onChange={(e) =>
                            setCity({
                                ...city,
                                coordinates: { ...city.coordinates, y: e.target.value },
                            })
                        }
                        required
                    />
                    {errors.coordinatesY && <span className="error">{errors.coordinatesY}</span>}
                </div>
                <div>
                    <label>Area*:</label>
                    <input
                        type="number"
                        value={city.area || ''}
                        onChange={(e) => setCity({ ...city, area: e.target.value })}
                        required
                    />
                    {errors.area && <span className="error">{errors.area}</span>}
                </div>
                <div>
                    <label>Population*:</label>
                    <input
                        type="number"
                        value={city.population || ''}
                        onChange={(e) => setCity({ ...city, population: e.target.value })}
                        required
                    />
                    {errors.population && <span className="error">{errors.population}</span>}
                </div>
                <div>
                    <label>Meters Above Sea Level:</label>
                    <input
                        type="number"
                        value={city.metersAboveSeaLevel || ''}
                        onChange={(e) => setCity({ ...city, metersAboveSeaLevel: e.target.value })}
                    />
                </div>
                <div>
                    <label>Establishment Date:</label>
                    <input
                        type="date"
                        value={city.establishmentDate || ''}
                        onChange={(e) => setCity({ ...city, establishmentDate: e.target.value })}
                    />
                </div>
                <div>
                    <label>Telephone Code*:</label>
                    <input
                        type="number"
                        value={city.telephoneCode || ''}
                        onChange={(e) => setCity({ ...city, telephoneCode: e.target.value })}
                        required
                    />
                    {errors.telephoneCode && <span className="error">{errors.telephoneCode}</span>}
                </div>
                <div>
                    <label>Climate:</label>
                    <select
                        value={city.climate}
                        onChange={(e) => setCity({ ...city, climate: e.target.value })}
                    >
                        {climates.map((climate) => (
                            <option key={climate} value={climate}>
                                {climate}
                            </option>
                        ))}
                    </select>
                </div>
                <div>
                    <label>Governor Name*:</label>
                    <input
                        type="text"
                        value={city.governor.name}
                        onChange={(e) =>
                            setCity({
                                ...city,
                                governor: { ...city.governor, name: e.target.value },
                            })
                        }
                        required
                    />
                    {errors.governorName && <span className="error">{errors.governorName}</span>}
                </div>
                <div>
                    <label>Governor Age*:</label>
                    <input
                        type="number"
                        value={city.governor.age || ''}
                        onChange={(e) =>
                            setCity({
                                ...city,
                                governor: { ...city.governor, age: e.target.value },
                            })
                        }
                        required
                    />
                    {errors.governorAge && <span className="error">{errors.governorAge}</span>}
                </div>
                <div>
                    <label>Governor Height*:</label>
                    <input
                        type="number"
                        value={city.governor.height || ''}
                        onChange={(e) =>
                            setCity({
                                ...city,
                                governor: { ...city.governor, height: e.target.value },
                            })
                        }
                        required
                    />
                    {errors.governorHeight && <span className="error">{errors.governorHeight}</span>}
                </div>
                <button type="submit">{cityId ? 'Update City' : 'Create City'}</button>
                <Link to="/" className="router-link">Cancel</Link>
            </form>
        </div>
    );
}

export default CityForm;
