import React, { useState, useEffect } from 'react';
import { Button } from 'primereact/button';
import { Accordion, AccordionTab } from 'primereact/accordion';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { ProgressSpinner } from 'primereact/progressspinner';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { declarationService } from '../services/declarationService';

const DeclarationDetails = ({ declaration: summary, onBack }) => {
  const [details, setDetails] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDetails = async () => {
      try {
        setLoading(true);
        // summary contains debur, deimpexp, derepert
        const data = await declarationService.getDeclarationDetails({
          debur: summary.debur,
          deimpexp: summary.deimpexp,
          derepert: summary.derepert
        });
        setDetails(data);
      } catch (error) {
        console.error("Error fetching declaration details:", error);
      } finally {
        setLoading(false);
      }
    };

    if (summary) {
      fetchDetails();
    }
  }, [summary]);

  const exportDetailsPDF = () => {
    if (!details) return;
    const doc = new jsPDF();
    const pageWidth = doc.internal.pageSize.getWidth();

    // TitRE PRINCIPAL
    doc.setFontSize(20);
    doc.setTextColor(15, 33, 71);
    doc.text(`Déclaration Douanière - Répertoire ${details.derepert}`, 14, 20);

    doc.setFontSize(10);
    doc.setTextColor(100);
    doc.text(`Généré le: ${new Date().toLocaleString()}`, 14, 28);
    doc.text(`Bureau: ${details.debur} | Opérateur: ${details.deimpexp}`, 14, 33);

    let currentY = 40;

    const addSection = (title, data) => {
      doc.setFontSize(14);
      doc.setTextColor(239, 121, 0); // Orange SINDA
      doc.text(title, 14, currentY);
      currentY += 5;

      autoTable(doc, {
        startY: currentY,
        body: data,
        theme: 'striped',
        styles: { fontSize: 9, cellPadding: 3 },
        columnStyles: { 0: { fontStyle: 'bold', width: 60 } },
        headStyles: { fillColor: [15, 33, 71] },
        margin: { left: 14, right: 14 }
      });
      currentY = doc.lastAutoTable.finalY + 10;
    };

    // Section 1: Informations Générales
    addSection("Informations Générales", [
      ["Bureau de dédouanement", details.debur],
      ["Numéro répertoire", details.derepert],
      ["Code opérateur", details.deimpexp],
      ["Type de déclaration", details.detypdec],
      ["Total articles", details.detotart],
      ["Numéro DAE", details.denumdae || 'N/A'],
      ["Date DAE", details.dedatdae ? new Date(details.dedatdae).toLocaleDateString() : 'N/A']
    ]);

    // Section 2: Parties Prenantes
    if (currentY > 240) { doc.addPage(); currentY = 20; }
    addSection("Parties Prenantes & Logistique", [
      ["Fournisseur", details.defourni],
      ["Agrément commissionnaire", details.deagr],
      ["Répertoire commissionnaire", details.derepagr],
      ["Nom et adresse déclarant", details.denomdec],
      ["Bureau frontière", details.deburfro],
      ["Bureau destination", details.deburdes],
      ["Pays provenance", details.depyprov],
      ["Pays achat", details.depyacha]
    ]);

    // Section 3: Financier
    if (currentY > 240) { doc.addPage(); currentY = 20; }
    addSection("Informations Financières", [
      ["Devise Facture Nette", details.dedevptfn],
      ["Prix Total Facture Nette", details.deptfn],
      ["Solde PTFN", `${details.desigptfn} ${details.desoldptfn}`],
      ["Fret (Devise/Montant)", `${details.dedevfret} / ${details.demonfret}`],
      ["Assurance (Devise/Montant)", `${details.dedevass} / ${details.demonass}`],
      ["Total TVD", details.dettvd],
      ["Total Liquidé", details.dettliq]
    ]);

    // Tables détaillées
    const addDetailTable = (title, head, body) => {
      if (body.length === 0) return;
      if (currentY > 220) { doc.addPage(); currentY = 20; }

      doc.setFontSize(14);
      doc.setTextColor(239, 121, 0);
      doc.text(title, 14, currentY);
      currentY += 5;

      autoTable(doc, {
        startY: currentY,
        head: [head],
        body: body,
        theme: 'grid',
        styles: { fontSize: 8 },
        headStyles: { fillColor: [15, 33, 71] }
      });
      currentY = doc.lastAutoTable.finalY + 10;
    };

    // Articles
    addDetailTable("Liste des Articles",
      ["No Art.", "Nomenclature", "Régime", "Poids Net", "Valeur Douane"],
      (details.articles || []).map(a => [a.danumart, a.danomencl, a.daregdecl, a.dapoinet, a.davald])
    );

    // Colis
    addDetailTable("Liste des Colis",
      ["No Ligne", "No Escale", "Rubrique", "No Colis"],
      (details.colis || []).map(c => [c.dcnumlm, c.dcnumesc, c.dcrubr, c.dcnumcp])
    );

    // Paiements
    addDetailTable("Liste des Paiements",
      ["No Quittance", "Date Quittance", "Montant"],
      (details.paiements || []).map(p => [p.dpnumqui, p.dpdatqui ? new Date(p.dpdatqui).toLocaleDateString() : '', p.dpmonpaie])
    );

    doc.save(`declaration_${details.derepert}.pdf`);
  };

  if (loading) {
    return (
      <div className="flex justify-content-center align-items-center" style={{ height: '400px' }}>
        <ProgressSpinner />
      </div>
    );
  }

  if (!details) return <div className="p-4">Erreur lors du chargement des détails.</div>;

  return (
    <>
      <Button
        label="RETOUR"
        icon="pi pi-arrow-left"
        className="return-btn orange"
        onClick={onBack}
      />

      <div className="details-title-bar">
        <div>
          <p className="details-subtitle">Détails</p>
          <h2 className="header-title">Déclaration No {details.derepert}</h2>
        </div>
        <div className="main-actions">
          <Button label="EXPORTER PDF" icon="pi pi-download" onClick={exportDetailsPDF} />
        </div>
      </div>

      <div className="info-box">
        <div><span>Bureau</span><strong>{details.debur}</strong></div>
        <div><span>Opérateur</span><strong>{details.deimpexp}</strong></div>
        <div><span>Répertoire</span><strong>{details.derepert}</strong></div>
        <div><span>Date Insertion</span><strong>{details.dedatin ? new Date(details.dedatin).toLocaleDateString() : 'N/A'}</strong></div>
      </div>

      <Accordion multiple activeIndex={[0, 1, 2, 3]}>
        {/* GRID 1: Bureau & DAE */}
        <AccordionTab header="Informations Générales">
          <div className="grid-container">
            <div className="grid-item"><span>Bureau de dédouanement:</span> <strong>{details.debur}</strong></div>
            <div className="grid-item"><span>Numéro répertoire du déclarant:</span> <strong>{details.derepert}</strong></div>
            <div className="grid-item"><span>Code en douane de l'opérateur:</span> <strong>{details.deimpexp}</strong></div>
            <div className="grid-item"><span>Type de déclaration:</span> <strong>{details.detypdec}</strong></div>
            <div className="grid-item"><span>Nombre total d'articles:</span> <strong>{details.detotart}</strong></div>
            <div className="grid-item"><span>Numéro DAE:</span> <strong>{details.denumdae || 'N/A'}</strong></div>
            <div className="grid-item"><span>Date d'enregistrement DAE:</span> <strong>{details.dedatdae ? new Date(details.dedatdae).toLocaleDateString() : 'N/A'}</strong></div>
          </div>
        </AccordionTab>

        {/* GRID 2: Parties Prenantes */}
        <AccordionTab header="Parties Prenantes & Logistique">
          <div className="grid-container">
            <div className="grid-item"><span>Fournisseur (Nom/Adr):</span> <strong>{details.defourni}</strong></div>
            <div className="grid-item"><span>Agrément commissionnaire:</span> <strong>{details.deagr}</strong></div>
            <div className="grid-item"><span>Répertoire commissionnaire:</span> <strong>{details.derepagr}</strong></div>
            <div className="grid-item"><span>Nom et adresse déclarant:</span> <strong>{details.denomdec}</strong></div>
            <div className="grid-item"><span>Bureau frontière:</span> <strong>{details.deburfro}</strong></div>
            <div className="grid-item"><span>Bureau de destination:</span> <strong>{details.deburdes}</strong></div>
            <div className="grid-item"><span>Pays de provenance:</span> <strong>{details.depyprov}</strong></div>
            <div className="grid-item"><span>Pays d'achat:</span> <strong>{details.depyacha}</strong></div>
          </div>
        </AccordionTab>

        {/* GRID 3: Informations Financières */}
        <AccordionTab header="Informations Financières">
          <div className="grid-container">
            <div className="grid-item"><span>Devise facture nette:</span> <strong>{details.dedevptfn}</strong></div>
            <div className="grid-item"><span>Prix total facture nette:</span> <strong>{details.deptfn}</strong></div>
            <div className="grid-item"><span>Signe algébrique (+/−):</span> <strong>{details.desigptfn}</strong></div>
            <div className="grid-item"><span>Montant du solde PTFN:</span> <strong>{details.desoldptfn}</strong></div>
            <div className="grid-item"><span>Devise du fret:</span> <strong>{details.dedevfret}</strong></div>
            <div className="grid-item"><span>Montant du fret:</span> <strong>{details.demonfret}</strong></div>
            <div className="grid-item"><span>Devise de l'assurance:</span> <strong>{details.dedevass}</strong></div>
            <div className="grid-item"><span>Montant de l'assurance:</span> <strong>{details.demonass}</strong></div>
            <div className="grid-item"><span>Cours de conversion:</span> <strong>{details.decourdev}</strong></div>
            <div className="grid-item"><span>Total TVD:</span> <strong>{details.dettvd}</strong></div>
            <div className="grid-item"><span>Total liquidé:</span> <strong>{details.dettliq}</strong></div>
          </div>
        </AccordionTab>

        {/* GRID 4: Colis & Livraison */}
        <AccordionTab header="Colis & Livraison">
          <div className="grid-container">
            <div className="grid-item"><span>Nombre total de colis:</span> <strong>{details.detotcoli}</strong></div>
            <div className="grid-item"><span>Mode de livraison:</span> <strong>{details.demodliv}</strong></div>
            <div className="grid-item"><span>Localisation marchandise:</span> <strong>{details.delocalis}</strong></div>
          </div>
        </AccordionTab>

        {/* LIST 1: Articles */}
        <AccordionTab header={`Liste des Articles (${details.articles?.length || 0})`}>
          <DataTable value={details.articles} responsiveLayout="stack" breakpoint="960px" stripedRows className="details-table">
            <Column field="danumart" header="No Art." sortable />
            <Column field="danomencl" header="Nomenclature" sortable />
            <Column field="daregdecl" header="Régime" sortable />
            <Column field="dapoinet" header="Poids Net" sortable />
            <Column field="davald" header="Valeur Douane" sortable />
          </DataTable>
        </AccordionTab>

        {/* LIST 2: Colis */}
        <AccordionTab header={`Liste des Colis (${details.colis?.length || 0})`}>
          <DataTable value={details.colis} responsiveLayout="stack" breakpoint="960px" stripedRows className="details-table">
            <Column field="dcnumlm" header="No Ligne" sortable />
            <Column field="dcnumesc" header="No Escale" sortable />
            <Column field="dcrubr" header="Rubrique" sortable />
            <Column field="dcnumcp" header="No Colis" sortable />
          </DataTable>
        </AccordionTab>

        {/* LIST 3: Paiements */}
        <AccordionTab header={`Liste des Paiements (${details.paiements?.length || 0})`}>
          <DataTable value={details.paiements} responsiveLayout="stack" breakpoint="960px" stripedRows className="details-table">
            <Column field="dpnumqui" header="No Quittance" sortable />
            <Column field="dpdatqui" header="Date Quittance" body={(row) => row.dpdatqui ? new Date(row.dpdatqui).toLocaleDateString() : ''} sortable />
            <Column field="dpmonpaie" header="Montant" sortable />
          </DataTable>
        </AccordionTab>

        {/* LIST 4: Récapitulatifs */}
        <AccordionTab header={`Liste des Récapitulatifs (${details.recapitulatifs?.length || 0})`}>
          <DataTable value={details.recapitulatifs} responsiveLayout="stack" breakpoint="960px" stripedRows className="details-table">
            <Column field="drcodtax" header="Code Taxe" sortable />
            <Column field="drmontax" header="Montant Taxe" sortable />
          </DataTable>
        </AccordionTab>
      </Accordion>
    </>
  );
};

export default DeclarationDetails;
