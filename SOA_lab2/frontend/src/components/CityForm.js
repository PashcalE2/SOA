import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function CityForm({ cityId }) {
    const [city, setCity] = useState({
        id: null,
        name: '',
        coordinates: { x: null, y: null },
        area: null,
        population: null,
        metersAboveSeaLevel: null,
        establishmentDate: null,
        telephoneCode: null,
        climate: '',
        governor: { name: '', age: null, height: null },
    });
    const [climates] = useState([
        'TROPICAL_SAVANNA',
        'HUMIDCONTINENTAL',
        'SUBARCTIC',
        'POLAR_ICECAP',
        'DESERT',
    ]);
    const [errors, setErrors] = useState({});
    const [isEditMode, setIsEditMode] = useState(false);

    useEffect(() => {
        if (cityId) {
            setIsEditMode(true);
            loadCity(cityId).then(() => {
                console.log("Cities fetched successfully.");
            }).catch((err) => {
                console.error("Error in fetching cities:", err);
            });
        }
    }, [cityId]);

    const loadCity = async (id) => {
        try {
            const response = await axios.get(`https://localhost:22601/cities/${id}`);
            setCity(response.data);
        } catch (error) {
            console.error("Error fetching city:", error);
        }
    };

    const validateFields = () => {
        const newErrors = {};
        let isValid = true;

        if (!city.name) {
            newErrors.name = 'Name is required.';
            isValid = false;
        }
        if (city.coordinates.x == null) {
            newErrors.coordinatesX = 'Coordinates X is required.';
            isValid = false;
        }
        if (city.coordinates.y == null) {
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

        const url = isEditMode
            ? `https://localhost:22601/cities/${city.id}`
            : `https://localhost:22601/cities`;
        const method = isEditMode ? 'put' : 'post';

        try {
            await axios[method](url, city);
            alert('City saved successfully!');
        } catch (error) {
            console.error("Error saving city:", error);
            alert("Error saving city: " + error.message);
        }
    };

    return (
        <div>
            <h1>{isEditMode ? 'Edit City' : 'Add City'}</h1>
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
                <button type="submit">{isEditMode ? 'Update City' : 'Create City'}</button>
                <Link to="/" className="router-link">Cancel</Link>
            </form>
        </div>
    );
}

export default CityForm;
