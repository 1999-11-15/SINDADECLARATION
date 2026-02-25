const API_BASE_URL = 'http://localhost:8080/api/declarations';

export const declarationService = {
    /**
     * Fetch all declarations
     * GET /api/declarations/all
     */
    getAllDeclarations: async () => {
        try {
            const response = await fetch(`${API_BASE_URL}/all`);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return await response.json();
        } catch (error) {
            console.error("Error fetching all declarations:", error);
            throw error;
        }
    },

    /**
     * Fetch declaration details by ID
     * GET /api/declarations/details
     * Composite ID: debur, deimpexp, derepert
     */
    getDeclarationDetails: async (id) => {
        try {
            const queryParams = new URLSearchParams(id).toString();
            const response = await fetch(`${API_BASE_URL}/details?${queryParams}`);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return await response.json();
        } catch (error) {
            console.error("Error fetching declaration details:", error);
            throw error;
        }
    },

    /**
     * Simple search Mode 1 — 3 fields (debur, denumdec, derepert)
     * GET /api/declarations/simple-search
     * Returns List<Decent> (plain array)
     */
    simpleSearch: async (params) => {
        try {
            const queryParams = new URLSearchParams(params).toString();
            const response = await fetch(`${API_BASE_URL}/simple-search?${queryParams}`);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return await response.json();
        } catch (error) {
            console.error("Error in simple search:", error);
            throw error;
        }
    },

    /**
     * IMP/EXP search Mode 2 — 4 fields (debur, deimpexp, derepert, dedatin)
     * GET /api/declarations/impexp-search
     * Returns List<Decent> (plain array)
     */
    impExpSearch: async (params) => {
        try {
            const queryParams = new URLSearchParams(params).toString();
            const response = await fetch(`${API_BASE_URL}/impexp-search?${queryParams}`);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return await response.json();
        } catch (error) {
            console.error("Error in IMP/EXP search:", error);
            throw error;
        }
    },

    /**
     * Advanced search with criteria (paginated)
     * POST /api/declarations/search
     */
    searchDeclarations: async (criteria, page = 0, size = 10, sortBy = 'dedatin', direction = 'DESC') => {
        try {
            const url = `${API_BASE_URL}/search?page=${page}&size=${size}&sortBy=${sortBy}&direction=${direction}`;
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(criteria),
            });
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return await response.json();
        } catch (error) {
            console.error("Error searching declarations:", error);
            throw error;
        }
    }
};
