import React, { useState } from 'react';
// HNA EL BADEL LEWWEL: FASSAKHNA EL IMPORT MTE3 REACT-ICONS JEMLA
// import { FaSearch, FaTrash, FaPlus, FaTimes } from 'react-icons/fa';

const SearchContainer = ({ onSearch, onClear }) => {
  const [open, setOpen] = useState(false);

  const [fields, setFields] = useState({
    bureau: '',
    codeOperateur: '',
    numRepertoire: '',
    // Advanced fields
    dcnumesc: '',
    dcrubr: '',
    numeroTCE: '',
    danomencl: '',
    dateRegul: '',
    detypdec: '',
    regime: '',
    startDate: '',
    endDate: ''
  });
  const [searchMode, setSearchMode] = useState('numDec');

  const [error, setError] = useState('');

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFields({ ...fields, [name]: value });
  };

  const handleSearchClick = () => {
    // Check if at least one field is filled (simple or advanced)
    const hasValue = Object.values(fields).some(val => val && val.trim() !== '');
    if (!hasValue) {
      setError('Veuillez remplir au moins un champ pour la recherche.');
      return;
    }

    setError('');
    onSearch(fields);
  };

  const toggleSearchMode = () => {
    if (searchMode === 'numDec') {
      setSearchMode('codeRep');
      setFields({ ...fields, numDeclaration: '' });
    } else {
      setSearchMode('numDec');
      setFields({ ...fields, codeOperateur: '', numRepertoire: '' });
    }
  };


  const handleClearClick = () => {
    setFields({
      bureau: '',
      codeOperateur: '',
      numRepertoire: '',
      dcnumesc: '',
      dcrubr: '',
      numeroTCE: '',
      danomencl: '',
      dateRegul: '',
      detypdec: '',
      regime: '',
      startDate: '',
      endDate: ''
    });
    setError('');
    onClear();
  };

  return (
    <div className="search-container">
      <div className="search-header">
        <h2>Trouver les déclarations</h2>
      </div>

      <div className='search-body'>
        <div className="simple-search">
          <input
            name="bureau"
            className="simple-input"
            placeholder="Bureau de dédouanement"
            value={fields.bureau}
            onChange={handleInputChange}
          />
          <input
            name="codeOperateur"
            className="simple-input"
            placeholder="Code en douane de l'opérateur"
            value={fields.codeOperateur}
            onChange={handleInputChange}
          />
          <input
            name="numRepertoire"
            className="simple-input"
            placeholder="Numéro répertoire du déclarant"
            value={fields.numRepertoire}
            onChange={handleInputChange}
          />
        </div>

        {error && <p className="error-message">{error}</p>}

        <div className="add-field-container">
          <button className="add-field-btn" onClick={toggleSearchMode}>
            <span>
              {searchMode === 'numDec' ? '+ Ajouter Imp / Exp' : 'X Utiliser Numero de Declaration'}
            </span>
          </button>
        </div>

        <div className={`advanced-search-toggle ${open ? "open" : ""}`} onClick={() => setOpen(!open)}>
          <span>Recherche avancée</span>
          <span className="toggle-arrow"></span>
        </div>

        {open && (
          <div className="advanced-search-form">
            <input name="dcnumesc" className="advanced-input" placeholder="escale" value={fields.dcnumesc} onChange={handleInputChange} />
            <input name="dcrubr" className="advanced-input" placeholder="rubrique" value={fields.dcrubr} onChange={handleInputChange} />
            <input name="numeroTCE" className="advanced-input" placeholder="TCE" value={fields.numeroTCE} onChange={handleInputChange} />
            <input name="danomencl" className="advanced-input" placeholder="NDP (Nomenclature)" value={fields.danomencl} onChange={handleInputChange} />
            <input name="dateRegul" className="advanced-input" placeholder="Date de régularisation" type="text" onFocus={(e) => e.target.type = 'date'} onBlur={(e) => e.target.type = 'text'} value={fields.dateRegul} onChange={handleInputChange} />
            <input name="detypdec" className="advanced-input" placeholder="Type déclaration" value={fields.detypdec} onChange={handleInputChange} />
            <input name="regime" className="advanced-input" placeholder="Régime" value={fields.regime} onChange={handleInputChange} />
            <div className="date-range-container" style={{ display: 'contents' }}>
              <input name="startDate" className="advanced-input" placeholder="Date début" type="text" onFocus={(e) => e.target.type = 'date'} onBlur={(e) => e.target.type = 'text'} value={fields.startDate} onChange={handleInputChange} />
              <input name="endDate" className="advanced-input" placeholder="Date fin" type="text" onFocus={(e) => e.target.type = 'date'} onBlur={(e) => e.target.type = 'text'} value={fields.endDate} onChange={handleInputChange} />
            </div>
          </div>
        )}

        <div className="search-actions">
          <button className="clear-btn" onClick={handleClearClick}>
            Effacer
          </button>
          <button className="search-btn" onClick={handleSearchClick}>
            Rechercher
          </button>
        </div>
      </div>
    </div>
  );
};

export default SearchContainer;
