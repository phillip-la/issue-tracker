function IssueList({ issues, onDeleteIssue }) {
    return (
        <ul>
            {
                issues.map(issue => (
                    <li key={issue.id}>
                        {issue.title} - {issue.priority}

                        <button
                            type="button"
                            onClick={() => onDeleteIssue(issue.id)}
                        >
                            Delete
                        </button>
                    </li>
                ))
            }
        </ul>
    )
}

export default IssueList