import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

function CityList() {
    const [cities, setCities] = useState([]);
    const [filterFields, setFilterFields] = useState('');
    const [filterValues, setFilterValues] = useState('');
    const [sortFields, setSortFields] = useState('');
    const [sortOrder, setSortOrder] = useState('ASCENDING');
    const [page, setPage] = useState(1);
    const [size] = useState(10);
    const [governorName, setGovernorName] = useState('');
    const [climate, setClimate] = useState('');
    const [error, setError] = useState(null);

    // Default values for empty fields
    const defaultSortFields = 'id';
    const defaultSortOrder = 'ASCENDING';
    const defaultGovernorName = 'unknown';
    const defaultClimate = 'TROPICAL_SAVANNA';

    const getCities = useCallback(async () => {
        try {
            const filterUrl = filterFields && filterValues
                ? `${filterFields}/${filterValues}`
                : 'all';
            const response = await axios.get(
                `https://localhost:22601/cities/${filterUrl}/${sortFields || defaultSortFields}/${sortOrder || defaultSortOrder}/${page}/${size}`,
                {
                    headers: {
                        'Accept': 'application/xml',
                        'Content-Type': 'application/xml'
                    }
                }
            );
            setCities(response.data); // Saving fetched cities
        } catch (err) {
            alert("Error fetching cities: " + err.message);
            console.error("Error fetching cities:", err);
        }
    }, [filterFields, filterValues, sortFields, sortOrder, page, size]);

    useEffect(() => {
        getCities().then(() => {
            console.log("Cities fetched successfully.");
        }).catch((err) => {
            console.error("Error in fetching cities:", err);
        });
    }, [filterFields, filterValues, sortFields, sortOrder, page]);

    const applyFilter = async () => {
        setPage(1);
        await getCities();
    };

    const sortCities = async () => {
        setPage(1);
        await getCities();
    };

    const deleteCitiesByGovernor = async () => {
        const govName = governorName || defaultGovernorName;
        try {
            await axios.delete(`https://localhost:22601/cities/delete-by-governor/${govName}`, {
                headers: {
                    'Accept': 'application/xml',
                    'Content-Type': 'application/xml'
                }
            });
            alert("Cities successfully deleted.");
            await getCities();
        } catch (err) {
            alert("Error deleting cities by governor: " + err.message);
            console.error("Error deleting cities by governor: ", err);
        }
    };

    const groupCitiesById = async () => {
        try {
            const response = await axios.get(`https://localhost:22601/cities/group-by-id`, {
                headers: {
                    'Accept': 'application/xml',
                    'Content-Type': 'application/xml'
                }
            });
            alert(JSON.stringify(response.data, null, 2));
        } catch (err) {
            alert("Error grouping cities by ID: " + err.message);
            console.error("Error grouping cities by ID: ", err);
        }
    };

    const countCitiesByClimate = async () => {
        const selectedClimate = climate || defaultClimate;
        try {
            const response = await axios.get(`https://localhost:22601/cities/count-by-climate/${selectedClimate}`, {
                headers: {
                    'Accept': 'application/xml',
                    'Content-Type': 'application/xml'
                }
            });
            alert(`Number of cities with climate greater than ${selectedClimate}: ${response.data}`);
        } catch (err) {
            alert("Error counting cities by climate: " + err.message);
            console.error("Error counting cities by climate: ", err);
        }
    };

    const navigate = useNavigate();

    const editCity = (id) => {
        navigate(`/edit-city/${id}`);
    };

    const deleteCity = async (id) => {
        try {
            await axios.delete(`https://localhost:22601/cities/${id}`, {
                headers: {
                    'Accept': 'application/xml',
                    'Content-Type': 'application/xml'
                }
            });
            alert(`City with ID ${id} deleted.`);
            await getCities();
        } catch (err) {
            alert("Error deleting city: " + err.message);
            console.error("Error deleting city: ", err);
        }
    };

    const handleSort = (field) => setSortFields(field);

    const nextPage = () => setPage((prevPage) => prevPage + 1);
    const previousPage = () => setPage((prevPage) => Math.max(prevPage - 1, 1));

    return (
        <div>
            <h1>Cities</h1>
            <Link to="/second-service" className="router-link route">Go to Second Service</Link>
            {error && <div className="error-message">{error}</div>}

            <div className="filter-sort-container">
                <div className="filter-container">
                    <label>Filter Fields:</label>
                    <input value={filterFields} onChange={(e) => setFilterFields(e.target.value)}
                           placeholder="e.g., name,population"/>
                    <label>Filter Values:</label>
                    <input value={filterValues} onChange={(e) => setFilterValues(e.target.value)}
                           placeholder="e.g., New York,100000"/>
                    <button onClick={applyFilter}>Apply Filter</button>
                </div>

                <div className="sort-container">
                    <label>Sort Fields:</label>
                    <input value={sortFields} onChange={(e) => setSortFields(e.target.value)}
                           placeholder="e.g., id,name"/>
                    <label>Sort Order:</label>
                    <select value={sortOrder} onChange={(e) => setSortOrder(e.target.value)}>
                        <option value="ASCENDING">Ascending</option>
                        <option value="DESCENDING">Descending</option>
                    </select>
                    <button onClick={sortCities}>Sort</button>
                </div>
            </div>

            <div className="governor-container">
                <label>Governor name to Delete:</label>
                <input value={governorName} onChange={(e) => setGovernorName(e.target.value)}
                       placeholder="Governor name"/>
                <button onClick={deleteCitiesByGovernor}>Delete Cities by Governor</button>
            </div>

            <div className="group-container">
                <button onClick={groupCitiesById}>Group Cities by ID</button>
            </div>

            <div className="climate-container">
                <label>Select Climate:</label>
                <select value={climate} onChange={(e) => setClimate(e.target.value)}>
                    <option disabled value="">Please select one</option>
                    <option value="TROPICAL_SAVANNA">Tropical Savanna</option>
                    <option value="HUMIDCONTINENTAL">Humid Continental</option>
                    <option value="SUBARCTIC">Subarctic</option>
                    <option value="POLAR_ICECAP">Polar Icecap</option>
                    <option value="DESERT">Desert</option>
                </select>
                <button onClick={countCitiesByClimate}>Count Cities by Climate</button>
            </div>

            <div className="table-container">
                <table>
                    <thead>
                    <tr>
                        <th onClick={() => handleSort('id')} style={{cursor: 'pointer'}}>ID</th>
                        <th onClick={() => handleSort('name')} style={{cursor: 'pointer'}}>Name</th>
                        <th onClick={() => handleSort('population')} style={{cursor: 'pointer'}}>Population</th>
                        <th onClick={() => handleSort('area')} style={{cursor: 'pointer'}}>Area</th>
                        <th onClick={() => handleSort('establishmentDate')} style={{cursor: 'pointer'}}>Establishment
                            Date
                        </th>
                        <th onClick={() => handleSort('metersAboveSeaLevel')} style={{cursor: 'pointer'}}>Meters Above
                            Sea Level
                        </th>
                        <th onClick={() => handleSort('telephoneCode')} style={{cursor: 'pointer'}}>Telephone Code</th>
                        <th onClick={() => handleSort('governor.name')} style={{cursor: 'pointer'}}>Governor</th>
                        <th onClick={() => handleSort('climate')} style={{cursor: 'pointer'}}>Climate</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {cities.map((city) => (
                        <tr key={city.id} onClick={() => editCity(city.id)} style={{cursor: 'pointer'}}>
                            <td>{city.id}</td>
                            <td>{city.name}</td>
                            <td>{city.population}</td>
                            <td>{city.area}</td>
                            <td>{city.establishmentDate}</td>
                            <td>{city.metersAboveSeaLevel}</td>
                            <td>{city.telephoneCode}</td>
                            <td>{city.governor ? city.governor.name : 'N/A'}</td>
                            <td>{city.climate}</td>
                            <td>
                                <button onClick={() => deleteCity(city.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>

            <div className="pagination">
                <button onClick={previousPage} disabled={page <= 1}>Previous</button>
                <button onClick={nextPage}>Next</button>
            </div>
            <Link to="/create-city" className="router-link">Create New City</Link>
        </div>
    );
}

export default CityList;
