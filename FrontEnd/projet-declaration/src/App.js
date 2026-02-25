import React, { useState } from 'react';
import PageHeader from './components/PageHeader';
import SearchContainer from './components/SearchContainer';
import ResultsTable from './components/ResultsTable';
import DeclarationDetails from './components/DeclarationDetails';
import './App.css';
import Navbar from "./components/Navbar";
import 'primeicons/primeicons.css'; // <-- AJOUTEZ CETTE LIGNE



import { declarationService } from './services/declarationService';

function App() {
  const [searchResults, setSearchResults] = useState([]);
  const [showResults, setShowResults] = useState(false);
  const [selectedDeclaration, setSelectedDeclaration] = useState(null);
  const [loading, setLoading] = useState(false);

  React.useEffect(() => {
    fetchAll();
  }, []);

  const fetchAll = async () => {
    setLoading(true);
    try {
      const data = await declarationService.getAllDeclarations();
      setSearchResults(mapDeclarations(data));
      setShowResults(true);
    } catch (error) {
      console.error("Failed to fetch declarations", error);
    } finally {
      setLoading(false);
    }
  };

  // Helper: map backend Decent objects to frontend table rows
  const mapDeclarations = (data) => {
    return data.map(item => {
      const firstArticle = item.articles && item.articles.length > 0 ? item.articles[0] : {};
      return {
        debur: item.debur,
        deimpexp: item.deimpexp,
        derepert: item.derepert,
        denumdec: item.denumdec,
        danumtcce: firstArticle.danumtcce || 'N/A',
        daregdecl: firstArticle.daregdecl || 'N/A',
        detypdec: item.detypdec,
        dedatin: item.dedatin ? new Date(item.dedatin).toISOString().split('T')[0] : 'N/A',
        danomencl: firstArticle.danomencl || 'N/A',
        id: `ID-${item.debur}-${item.deimpexp}-${item.derepert}`,
        original: item
      };
    });
  };

  const handleSearch = async (fields) => {
    console.log("SEARCH START", fields);
    setLoading(true);
    try {
      const { searchMode } = fields;

      // --- Check if any advanced search field is filled ---
      const advancedFields = ['dcnumesc', 'dcrubr', 'numeroTCE', 'danomencl', 'detypdec', 'regime', 'startDate', 'endDate'];
      const hasAdvanced = advancedFields.some(key => fields[key] && fields[key].trim() !== '');

      let data;

      if (hasAdvanced) {
        // ── Advanced search → POST /search (paginated) ──
        const criteria = {
          debur: fields.bureau ? parseInt(fields.bureau) : null,
          derepert: fields.numRepertoire ? parseInt(fields.numRepertoire) : null,
          denumdec: fields.numDeclaration || null,
          deimpexp: fields.codeOperateur || null,
          dedatin: fields.dateDec ? `${fields.dateDec}T00:00:00` : null,
          numeroTCE: fields.numeroTCE || null,
          danomencl: fields.danomencl || null,
          detypdec: fields.detypdec || null,
          daregdecl: fields.regime ? parseInt(fields.regime) : null,
          dcnumesc: fields.dcnumesc ? parseInt(fields.dcnumesc) : null,
          dcrubr: fields.dcrubr ? parseInt(fields.dcrubr) : null,
          startDate: fields.startDate ? `${fields.startDate}T00:00:00` : null,
          endDate: fields.endDate ? `${fields.endDate}T23:59:59` : null
        };
        const cleanCriteria = Object.fromEntries(
          Object.entries(criteria).filter(([_, v]) => v !== null)
        );
        const result = await declarationService.searchDeclarations(cleanCriteria);
        data = result.content; // Page<Decent> → .content

      } else if (searchMode === 'impExp') {
        // ── IMP/EXP search → GET /impexp-search (4 fields) ──
        const params = {};
        if (fields.bureau) params.debur = fields.bureau;
        if (fields.codeOperateur) params.deimpexp = fields.codeOperateur;
        if (fields.numRepertoire) params.derepert = fields.numRepertoire;
        if (fields.dateDec) params.dedatin = `${fields.dateDec}T00:00:00`;

        if (Object.keys(params).length === 0) {
          await fetchAll();
          return;
        }
        data = await declarationService.impExpSearch(params);

      } else {
        // ── Simple search → GET /simple-search (3 fields) ──
        const params = {};
        if (fields.bureau) params.debur = fields.bureau;
        if (fields.numDeclaration) params.denumdec = fields.numDeclaration;
        if (fields.dateDec) params.dedatin = `${fields.dateDec}T00:00:00`;

        if (Object.keys(params).length === 0) {
          await fetchAll();
          return;
        }
        data = await declarationService.simpleSearch(params);
      }

      setSearchResults(mapDeclarations(data));
      setShowResults(true);

    } catch (error) {
      console.error("Search failed", error);
    } finally {
      setLoading(false);
    }
  };

  const handleClear = () => {
    fetchAll();
  };

  const handleSelectDeclaration = (declaration) => {
    setSelectedDeclaration(declaration);
  };

  const handleReturnToSearch = () => {
    setSelectedDeclaration(null);
  };

  return (
    <>
      <Navbar />

      <div className="page-container">
        {selectedDeclaration ? (
          <DeclarationDetails
            declaration={selectedDeclaration}
            onBack={handleReturnToSearch}
          />
        ) : (
          <>
            <PageHeader
              subtitle="Déclarations"
              title="Rechercher la déclaration"
            />

            <SearchContainer
              onSearch={handleSearch}
              onClear={handleClear}
            />

            {showResults && (
              <ResultsTable
                results={searchResults}
                onSelect={handleSelectDeclaration}
              />
            )}
          </>
        )}
      </div>
    </>
  );

}

export default App;
