import React, { useState, useRef } from "react";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { Button } from "primereact/button";
import { Calendar } from "primereact/calendar";
import { FilterMatchMode } from "primereact/api";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

const ResultsTable = ({ results = [], onSelect }) => {
  const dt = useRef(null);

  const [filters, setFilters] = useState({
    debur: { value: null, matchMode: FilterMatchMode.EQUALS },
    deimpexp: { value: null, matchMode: FilterMatchMode.CONTAINS },
    derepert: { value: null, matchMode: FilterMatchMode.CONTAINS },
    denumdec: { value: null, matchMode: FilterMatchMode.CONTAINS },
    danumtcce: { value: null, matchMode: FilterMatchMode.CONTAINS },
    daregdecl: { value: null, matchMode: FilterMatchMode.EQUALS },
    detypdec: { value: null, matchMode: FilterMatchMode.CONTAINS },
    dedatin: { value: null, matchMode: FilterMatchMode.DATE_IS },
    danomencl: { value: null, matchMode: FilterMatchMode.CONTAINS }
  });

  /* ========================= */
  /* EXPORT PDF */
  /* ========================= */

  const exportPDF = () => {
    const doc = new jsPDF('landscape');

    const columns = [
      "Bureau",
      "Code Opérateur",
      "Num. Répertoire",
      "Num. Serial",
      "Num. TITRE CCE",
      "Code Régime",
      "Type Décl.",
      "Date Insertion",
      "Nomenclature"
    ];

    const rows = results.map((r) => [
      r.debur,
      r.deimpexp,
      r.derepert,
      r.denumdec,
      r.danumtcce,
      r.daregdecl,
      r.detypdec,
      r.dedatin,
      r.danomencl
    ]);

    autoTable(doc, {
      head: [columns],
      body: rows,
      styles: { fontSize: 8 }
    });

    doc.save("declarations_sinda.pdf");
  };

  const dateFilterTemplate = (options) => (
    <Calendar
      value={options.value}
      onChange={(e) => options.filterCallback(e.value)}
      dateFormat="yy-mm-dd"
      placeholder="Date"
      className="p-column-filter"
    />
  );

  return (
    <div className="results-container">
      <DataTable
        ref={dt}
        value={results}
        paginator
        rows={10}
        rowsPerPageOptions={[5, 10, 25, 50]}
        sortMode="multiple"
        removableSort
        filters={filters}
        filterDisplay="menu"
        onFilter={(e) => setFilters(e.filters)}
        selectionMode="single"
        onRowSelect={(e) => onSelect && onSelect(e.data)}
        dataKey="id"
        emptyMessage="Aucune donnée trouvée"
        className="results-datatable"
        paginatorTemplate="RowsPerPageDropdown CurrentPageReport FirstPageLink PrevPageLink NextPageLink LastPageLink"
        currentPageReportTemplate="{first} - {last} de {totalRecords}"
        responsiveLayout="stack"
        breakpoint="960px"
        header={
          <div className="results-table-header">
            <h2>Résultats de la recherche de déclarations</h2>
            <Button
              label="Exporter"
              icon="pi pi-download"
              className="p-button-outlined p-button-warning"
              onClick={exportPDF}
            />
          </div>
        }
      >
        <Column field="debur" header="Bureau de dédouanement" sortable filter />
        <Column field="deimpexp" header="Code en douane de l’opérateur" sortable filter />
        <Column field="derepert" header="Numéro répertoire du déclarant" sortable filter />
        <Column field="denumdec" header="Numéro serial déclaration" sortable filter />
        <Column field="danumtcce" header="Numéro TITRE CCE" sortable filter />
        <Column field="daregdecl" header="Code régime déclaration" sortable filter />
        <Column field="detypdec" header="Type de déclaration" sortable filter />
        <Column
          field="dedatin"
          header="Date insertion (default sysdate)"
          sortable
          filter
          dataType="date"
          filterElement={dateFilterTemplate}
        />
        <Column field="danomencl" header="Nomenclature produit/article" sortable filter />
      </DataTable>
    </div>
  );
};

export default ResultsTable;