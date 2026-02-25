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
      // Map backend data to frontend structure for the 9-column table
      const mappedData = data.map(item => {
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
          // Keep original and metadata for internal use if needed
          id: `ID-${item.debur}-${item.deimpexp}-${item.derepert}`,
          original: item
        };
      });
      setSearchResults(mappedData);
      setShowResults(true);
    } catch (error) {
      console.error("Failed to fetch declarations", error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (fields) => {
    console.log("SEARCH START", fields);
    setLoading(true);
    try {
      // Map frontend field names to DecentSearchDTO fields
      const criteria = {
        debur: fields.bureau ? parseInt(fields.bureau) : null,
        deimpexp: fields.codeOperateur || null,
        derepert: fields.numRepertoire ? parseInt(fields.numRepertoire) : null,
        // Advanced fields
        numeroTCE: fields.numeroTCE || null,
        danomencl: fields.danomencl || null,
        detypdec: fields.detypdec || null,
        daregdecl: fields.regime ? parseInt(fields.regime) : null,
        // Manifest fields (JOIN Decoli)
        dcnumesc: fields.dcnumesc ? parseInt(fields.dcnumesc) : null,
        dcrubr: fields.dcrubr ? parseInt(fields.dcrubr) : null,
        // Dates
        startDate: fields.startDate ? `${fields.startDate}T00:00:00` : null,
        endDate: fields.endDate ? `${fields.endDate}T23:59:59` : null
      };

      // Check if any criteria is provided
      const hasCriteria = Object.values(criteria).some(val => val !== null);

      if (!hasCriteria) {
        await fetchAll();
      } else {
        const data = await declarationService.searchDeclarations(criteria);
        const mappedData = data.content.map(item => {
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
        setSearchResults(mappedData);
        setShowResults(true);
      }
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
